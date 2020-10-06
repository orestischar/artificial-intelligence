
 /*the facts that we store at the prolog database are
	direction(Id,D),
	next(X1,Y1,X2,Y2,Id),
	trafficcost(Id,C),
	cost(Id,C),
	valid(Id)),
	rating(Id,R) */
	

looktraffic(09:00-11:00=Morning,13:00-15:00=Midday,17:00-19:00=Afternoon,Time,C):-
	((Time >= 9, Time < 11) ->
		(Morning = high -> C=3; Morning = medium -> C=2; Morning = low -> C=1);
	 (Time >= 13, Time < 15) ->
		(Midday = high -> C=3; Midday = medium -> C=2; Midday = low -> C=1);
	 (Time >= 17, Time < 19) ->
		(Afternoon = high -> C=3; Afternoon = medium -> C=2; Afternoon = low -> C=1);
	  (C=0) ).

candrive(_,Kind,_,_,_,_,_,0,0,_,_,0,_,_,_,0,0,_):-
	Kind = primary, !;
	Kind= primary_link, !;
	Kind = secondary, !;
	Kind = secondary_link, !;
	Kind = tertiary, !;
	Kind = tertiary_link, !;
	Kind = motorway, !;
	Kind = motorway_link, !;
	Kind = trunk, !;
	Kind = trunk_link, !;
	Kind = residential, !;
	Kind = unclassified, !.
	
suitable(A,From,To,Clients,T_lang,C_lang,Lag,Type):-
	A = yes,
	Clients >= From,
	Clients =< To,
	member(C_lang,T_lang),
	(((Lag > 5) -> Type = minivan);
	((Lag > 3, Lag =< 5) -> (Type = minivan; Type = large));
	((Lag =< 3) -> (Type = subcompact; Type = compact; Type = minivan; Type = large))),!.
	

roadcost(Time,_,Kind,_,_,Light,Lanes,Maxspeed,_,_,_,_,_,_,_,_,_,_,Toll,TC,C):-
	((Kind = primary; Kind = primary_link) -> F = 1 ;
	 (Kind = secondary; Kind = secondary_link ) -> F = 1.2 ;
	 (Kind = tertiary; Kind = tertiary_link ) -> F = 1.5 ;
	 (Kind = motorway; Kind = motorway_link; Kind = trunk; Kind = trunk_link) -> F = 1.8;
	  F = 2 ),
	
	( Time < 18 -> S = 0; 
	 (Time >= 18 , Light = yes) -> S = 0;
	  S = 2 ),
	  
	(Lanes = 1 -> T = 3;
	 Lanes = 2 -> T = 2;
	 T = 1 ),
	  
	(Maxspeed >= 80 -> R = 1; 
	 (Maxspeed > 40, Maxspeed < 80) -> R = 2;
	 R = 3 ),
	 
	(Toll = yes -> K=2;
	 K=0 ),
	 
	C is F + S + T + R + K + TC.
      
	
	  
	
	 
	 

