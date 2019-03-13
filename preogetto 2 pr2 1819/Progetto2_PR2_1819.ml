type ide = string;;

type exp = 
	| Eint             of int 
	| Ebool            of bool 
	| Den              of ide 
	| Prod             of exp * exp 
	| Sum              of exp * exp 
	| Diff             of exp * exp 
	| Eq               of exp * exp 
	| Minus            of exp 
	| IsZero           of exp 
	| Or               of exp * exp 
	| And              of exp * exp 
	| Not              of exp 
	| Ifthenelse       of exp * exp * exp 
	| Let              of ide * exp * exp 
	| Fun              of ide * exp 
	| FunCall          of exp * exp 
	| Letrec           of ide * exp * exp
	(*Nuove Operazioni per Dizionario*)
    | Estring          of string
	| Dict             of (ide * exp) list
	| Insert           of exp * ide * exp
	| Get              of exp * ide
	| Remove     	   of exp * ide
	| Clear            of exp
	| ApplyOver        of exp * exp
	(*Nuove operazioni per Scoping Dinamico*)
	| DynamicFun       of ide * exp
	| DynamicFunCall   of exp * exp
	| DynamicLetRec    of ide * exp * exp
	| DynamicApplyOver of exp * exp;;

(*ambiente polimorfo*)
type 't env = ide -> 't;;

let emptyenv (v : 't) = function x -> v;;

let applyenv (r : 't env) (i : ide) = r i;;

let bind (r : 't env) (i : ide) (v : 't) = function x -> if x = i then v else applyenv r x;;

(*tipi esprimibili*)
type evT = 
    | Unbound
	| Int              of int 
	| Bool             of bool 
	| FunVal           of evFun 
	| RecFunVal        of ide * evFun
	(*Nuovi Tipi Esprimibili per Dizionario*)
    | String           of string
	| DictVal          of (ide * evT) list
	(*Nuovi Tipi Esprimibili per Scoping Dinamico*)
	| DynamicFunVal    of ide * exp
	| DynamicRecFunVal of ide * (ide * exp)
and evFun = ide * exp * evT env;;

(*rts*)

(*type checking*)

let typecheck (s : string) (v : evT) : bool =
     match s with
	    | "int" -> (match v with
		    | Int(_) -> true 
		    | _ -> false) 
	    | "bool" -> (match v with
		    | Bool(_) -> true 
		    | _ -> false)
	    | _ -> failwith("not a valid type");;

(*funzioni primitive*)

let prod x y =
     if (typecheck "int" x) && (typecheck "int" y)
	    then (match (x,y) with
		    | (Int(n),Int(u)) -> Int(n*u)
		    | (_,_)->failwith("error"))
	    else failwith("Type error");;

let sum x y = 
    if (typecheck "int" x) && (typecheck "int" y)
	    then (match (x,y) with
		    | (Int(n),Int(u)) -> Int(n+u)
		    | (_,_)->failwith("error"))
	    else failwith("Type error");;

let diff x y = 
    if (typecheck "int" x) && (typecheck "int" y)
	    then (match (x,y) with
		    | (Int(n),Int(u)) -> Int(n-u)
		    | (_,_)->failwith("error"))
	    else failwith("Type error");;

let eq x y =
    if (typecheck "int" x) && (typecheck "int" y)
	    then (match (x,y) with
		    | (Int(n),Int(u)) -> Bool(n=u)
		    | (_,_)->failwith("error"))
	    else failwith("Type error");;

let minus x = 
    if (typecheck "int" x) 
	    then (match x with
	   	    | Int(n) -> Int(-n)
	   	    | _->failwith("error"))
	    else failwith("Type error");;

let iszero x = 
    if (typecheck "int" x)
	    then (match x with
		    | Int(n) -> Bool(n=0)
		    | _->failwith("error"))
	    else failwith("Type error");;

let vel x y = 
    if (typecheck "bool" x) && (typecheck "bool" y)
	    then (match (x,y) with
		    | (Bool(b),Bool(e)) -> (Bool(b||e))
		    | (_,_)->failwith("error"))
	    else failwith("Type error");;

let et x y = 
    if (typecheck "bool" x) && (typecheck "bool" y)
	    then (match (x,y) with
		    | (Bool(b),Bool(e)) -> Bool(b&&e)
		    | (_,_)->failwith("error"))
	    else failwith("Type error");;

let non x = 
    if (typecheck "bool" x)
	    then (match x with
		    | Bool(true) -> Bool(false)
		    | Bool(false) -> Bool(true)
		    | _->failwith("error"))
	    else failwith("Type error");;

(* interprete *)
let rec eval (e : exp) (r : evT env) : evT = match e with
	| Eint n -> Int n
	| Ebool b -> Bool b 
	| IsZero a -> iszero (eval a r) 
	| Den i -> applyenv r i 
	| Eq(a, b) -> eq (eval a r) (eval b r) 
	| Prod(a, b) -> prod (eval a r) (eval b r) 
	| Sum(a, b) -> sum (eval a r) (eval b r) 
	| Diff(a, b) -> diff (eval a r) (eval b r) 
	| Minus a -> minus (eval a r) 
	| And(a, b) -> et (eval a r) (eval b r) 
	| Or(a, b) -> vel (eval a r) (eval b r) 
	| Not a -> non (eval a r) 
	| Ifthenelse(a, b, c) -> 
		let g = (eval a r) in
			if (typecheck "bool" g) 
				then (if g = Bool(true) then (eval b r) else (eval c r))
				else failwith ("nonboolean guard")
	| Let(i, e1, e2) -> eval e2 (bind r i (eval e1 r)) 
	| Fun(i, a) -> FunVal(i, a, r) 
	| FunCall(f, eArg) -> 
		let fClosure = (eval f r) in
			(match fClosure with
				| FunVal(arg, fBody, fDecEnv) -> 
					eval fBody (bind fDecEnv arg (eval eArg r)) 
				| RecFunVal(g, (arg, fBody, fDecEnv)) -> 
					let aVal = (eval eArg r) in
						let rEnv = (bind fDecEnv g fClosure) in
							let aEnv = (bind rEnv arg aVal) in
								eval fBody aEnv 
				| _ -> failwith("non functional value")) 
	| Letrec(f, funDef, leBody) ->
    	(match funDef with
				| Fun(i, fBody) -> let r1 = (bind r f (RecFunVal(f, (i, fBody, r)))) in
	    		    eval leBody r1
				| _ -> failwith("non functional def"))
	(*Implementazione Nuove Operazioni*)
    | Estring s -> String s
	| Dict(list) -> let rec evalist (lst : (ide * exp) list) : (ide * evT) list = 
		(match lst with
			| [] -> [] 
			| (id, arg) :: rest -> (id, (eval arg r)) :: evalist rest) 
		in DictVal(evalist list)
	| Clear(dict) -> DictVal([])
	| Insert(dict, nome, valore) -> 
        (match (eval dict r) with
		    | DictVal(list) ->  let rec insert (lst : (ide * evT) list) (nome : ide) (valore : exp) : (ide * evT) list = 
				(match lst with
			        | [] -> [(nome, eval valore r)]
					| (id, arg) :: rest -> if (nome = id) then failwith ("name already exist")
		    								else (id, arg) :: insert rest nome valore)
				 	in DictVal(insert list nome valore)
			| _ -> failwith("wrong select value"))
	| Get(dict, nome) -> 
        (match (eval dict r) with 
			| DictVal(list) -> let rec lookup (nom : ide) (lista : (ide * evT) list) : evT = 
				(match lista with 
					| [] -> Unbound 
					| (id, vals) :: rest -> if (nom = id) then vals									
											else lookup nome rest)
					in lookup nome list  
			| _ -> failwith("wrong select value")) 
	| Remove(dict, nome) -> 
        (match (eval dict r) with
			| DictVal(list) -> let rec remove (nome : ide) (lista : (ide * evT) list) : (ide * evT) list = 
				(match lista with 
				    | [] -> []
					|(id, vals):: rest -> if (nome = id) then rest 
										  else (id, vals) :: remove nome rest)
					in DictVal(remove nome list)
			| _ -> failwith("wrong select value"))
	| ApplyOver(f, dict) ->	
        (match (eval dict r) with 
			| DictVal(list) ->let f1 = (eval f r)
				in let applyfun (f : evT) (v : evT) : (evT) =
					(match f with
						| FunVal(arg, fBody, fDecEnv) -> eval fBody (bind fDecEnv arg v)
						| RecFunVal(g, (arg, fBody, fDecEnv)) ->
							let rEnv = (bind fDecEnv g f)
								in let aEnv = (bind rEnv arg v) 
								    in eval fBody aEnv
						| _ -> failwith("non functional value"))
					in let rec apply (lista : (ide * evT) list) (f : evT): (ide * evT) list = 
						(match lista with 
							| [] ->[]
							| (id, arg) :: xs -> try (id, applyfun f arg) :: (apply xs f) with Failure "Type error" -> (id, arg) :: (apply xs f))
								in DictVal(apply list f1)   
							| _ -> failwith("Type error"));;

(*Interprete con scoping dinamico*)
let rec rteval (e : exp) (r : evT env) : evT = match e with
	| Eint n -> Int n
	| Ebool b -> Bool b 
	| IsZero a -> iszero (rteval a r) 
	| Den i -> applyenv r i 
	| Eq(a, b) -> eq (rteval a r) (rteval b r) 
	| Prod(a, b) -> prod (rteval a r) (rteval b r) 
	| Sum(a, b) -> sum (rteval a r) (rteval b r) 
	| Diff(a, b) -> diff (rteval a r) (rteval b r) 
	| Minus a -> minus (rteval a r) 
	| And(a, b) -> et (rteval a r) (rteval b r) 
	| Or(a, b) -> vel (rteval a r) (rteval b r) 
	| Not a -> non (rteval a r) 
	| Ifthenelse(a, b, c) -> 
		let g = (rteval a r) in
			if (typecheck "bool" g) 
				then (if g = Bool(true) then (rteval b r) else (rteval c r))
				else failwith ("nonboolean guard")
	| Let(i, e1, e2) -> rteval e2 (bind r i (rteval e1 r))
    | Estring s -> String s
	| Dict(list) -> let rec evalist (lst : (ide * exp) list) : (ide * evT) list = 
		(match lst with
			| [] -> [] 
			| (id, arg) :: rest -> (id, (rteval arg r)) :: evalist rest) 
		in DictVal(evalist list)
	| Clear(dict) -> DictVal([])
	| Insert(dict, nome, valore) -> 
        (match (rteval dict r) with
		    | DictVal(list) ->  let rec insert (lst : (ide * evT) list) (nome : ide) (valore : exp) : (ide * evT) list = 
				(match lst with
			        | [] -> [(nome, rteval valore r)]
					| (id, arg) :: rest -> if (nome = id) then failwith ("name already exist")
		    								else (id, arg) :: insert rest nome valore)
				 	in DictVal(insert list nome valore)
			| _ -> failwith("wrong select value"))
	| Get(dict, nome) -> 
        (match (rteval dict r) with 
			| DictVal(list) -> let rec lookup (nom : ide) (lista : (ide * evT) list) : evT = 
				(match lista with 
					| [] -> Unbound 
					| (id, vals) :: rest -> if (nom = id) then vals									
											else lookup nome rest)
					in lookup nome list  
			| _ -> failwith("wrong select value")) 
	| Remove(dict, nome) -> 
        (match (rteval dict r) with
			| DictVal(list) -> let rec remove (nome : ide) (lista : (ide * evT) list) : (ide * evT) list = 
				(match lista with 
				    | [] -> []
					|(id, vals):: rest -> if (nome = id) then rest 
										  else (id, vals) :: remove nome rest)
					in DictVal(remove nome list)
			| _ -> failwith("wrong select value"))
	(*Implementazione Nuove Operazioni*)
	| DynamicFun(i,a) -> DynamicFunVal(i,a)
    | DynamicFunCall (f, eArg) ->
        let fClosure = (rteval f r) in
            (match fClosure with
                | DynamicFunVal(arg, fBody) ->
                    let v = (rteval eArg r) in rteval fBody (bind r arg v)
                | DynamicRecFunVal (g, (arg, fBody)) ->
                    let aVal = (rteval eArg r) in
                        let rEnv = (bind r g fClosure) in
                            let aEnv = (bind rEnv arg aVal) in 
                                rteval fBody aEnv   
                | _ -> failwith ("non functional value"))
    | DynamicLetRec(f, funDef, leBody) ->
        (match funDef with 
            | Fun(i, fBody) ->
                let r1 = (bind r f (DynamicRecFunVal(f, (i, fBody)))) in
                        rteval leBody r1
            | _-> failwith("non functional definition"))  		
	| ApplyOver(f, dict) ->	
        (match (rteval dict r) with 
			| DictVal(list) ->let f1 = (rteval f r)
				in let applyfun (f : evT) (v : evT) : (evT) =
					(match f with
						| DynamicFunVal(arg, fBody) -> rteval fBody (bind r arg v)
						| DynamicRecFunVal(g, (arg, fBody)) ->
							let rEnv = (bind r g f)
								in let aEnv = (bind rEnv arg v) 
								    in rteval fBody aEnv
						| _ -> failwith("non functional value"))
					in let rec apply (lista : (ide * evT) list) (f : evT): (ide * evT) list = 
						(match lista with 
							| [] ->[]
							| (id, arg) :: xs -> try (id, applyfun f arg) :: (apply xs f) with Failure "Type error" -> (id, arg) :: (apply xs f))
								in DictVal(apply list f1)   
							| _ -> failwith("Type error"));;

(* =============================  TESTS BASE ================= *)

let env0 = emptyenv Unbound;;

let e1 = FunCall(Fun("y", Sum(Den "y", Eint 1)), Eint 3);;

eval e1 env0;;

let e2 = FunCall(Let("x", Eint 2, Fun("y", Sum(Den "y", Den "x"))), Eint 3);;

eval e2 env0;;

(*============================== TEST DIZIONARIO ===================*)

let env1 = emptyenv Unbound;;

let dict0 = Dict([]);;

let dict1 = Dict([("Nome", Estring "Marcello"); ("Matricola", Eint 546273)]);;

let dict2 = Insert(dict1, "Cognome", Estring "Matteucci");;

let dict3 = Insert(dict1, "Nome", Estring "Matteucci");;

let dict4 = Insert(dict1, "AnnoNascita", Eint 1997);;

let dict5 = Insert(dict1, "FuoriCorso", Ebool false);;

eval dict1 env1;;

eval dict2 env1;;

eval dict3 env1;;

eval dict4 env1;;

eval dict5 env1;;

let dict6 = Clear(dict1);;

eval dict6 env1;;

eval (Get (dict2, "Nome")) env1;;

eval (Get (dict2, "AnnoNascita")) env1;;

eval (Get (dict4, "AnnoNascita")) env1;;

let dict7 = Remove(dict1, "Nome");;

eval dict7 env1;;

let f1 = Fun("x", Sum(Den "x", Eint 1));;

let f2 = Fun("x", Not (Den "x"));;

eval (ApplyOver(f1, dict7)) env1;;

eval (ApplyOver(f1, dict1)) env1;;

eval (ApplyOver(f1, dict4)) env1;;

eval (ApplyOver(f2, dict5)) env1;;

(*============== TEST SCOPING DINAMICO =================*)

let env2 = emptyenv Unbound;;

let re1 = DynamicFunCall(DynamicFun("y", Sum(Den "y", Eint 1)), Eint 3);;

rteval re1 env2;;

let rf1 = DynamicFun("y", Sum(Den "y", Eint 1));;

let rf2 = DynamicFun("y", Not(Den "x"));;

let re2 = DynamicFunCall(rf1, Eint 3);;

rteval re2 env2;;

let rdict0 = Dict([]);;

let rdict1 = Dict([("Nome", Estring "Marcello"); ("Matricola", Eint 546273)]);;

rteval (ApplyOver(rf1, rdict1)) env2;;

let rdict2 = Insert(rdict1, "FuoriCorso", Ebool false);;

rteval (ApplyOver(rf2, rdict2)) env2;;