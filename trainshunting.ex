
defmodule Trainshunting do

	
	def take([h|t], n) do
		if n > 0 do
			n = n - 1
			[h| take(t, n)] 
		else 
			[]

		end

	end

	def take(list, 0) do
		list
	end

	def take([], _) do
		[]
	end

	def drop(list, 0) do
		list
	end

	def drop([], _) do
		[]
	end

	def drop([h|t], n) do
		if n > 0 do
			n = n - 1
			drop(t, n)
		else
			[h|t]
		end
	end


	def append(xs, ys) do
		xs ++ ys
	end

	def member([], _) do
		false
	end

	def member([h|t], ys) do
		if ys == h do
			true
		else
			member(t, ys)
		end
	end

	def position([y|_], y) do
		1
	end

	def position([h|t], y) do
		1 + position(t, y)
	end

end

defmodule Moves do

	def one([]) do 
		[]
	end

	def one([h]) do 
		[h]
	end

	def one([h|t]) do 
		[h|t]
	end

	def length([h], i) do
		i = i + 1
		i
	end

	def length([h|t], i) do
		i = i + 1
		length(t, i)
	end


	def single({:one, n}, main, one, two) do
		if  n > 0  do
			Trainshunting.take(main, length(main, 0) - n)
			#one = Trainshunting.drop(main, n) ++ one
		else 
			main ++ Trainshunting.take(one, 0 - n)
			#one = Trainshunting.drop(one, 0 - n)
		end 
	end

	def single1({:one, n}, main, one, two) do
		if  n > 0  do
			#Trainshunting.take(main, length(main, 0) - n)
			one = Trainshunting.drop(main, n) ++ one
		else 
			#main ++ Trainshunting.take(one, 0 - n)
			one = Trainshunting.drop(one, 0 - n)
		end 
	end

	def single1({:two, n}, main, one, two) do
		two
	end

	

	def single({:two, n}, main, one, two) do
		if  n > 0  do
			Trainshunting.take(main, length(main, 0) - n)
			#two = Trainshunting.drop(main, n) ++ two
		else 
			main ++ Trainshunting.take(one, 0 - n)
			#two = Trainshunting.drop(two, 0 - n)
		end 
	end

	def single2({:two, n}, main, one, two) do
		if  n > 0  do
			#Trainshunting.take(main, length(main, 0) - n)
			one = Trainshunting.drop(main, n) ++ two
		else 
			#main ++ Trainshunting.take(two, 0 - n)
			one = Trainshunting.drop(two, 0 - n)
		end 
	end


	def single2({:one, n}, main, one, two) do
		one
	end


	def moves([h], main, one, two) do
		h
	end

	
	
	def moves([h|t], main, one, two) do
		
		[{main, one, two}| moves(t, single(h, main, one, two), single1(h, main, one, two), single2(h, main, one, two))]
		
		#[{single(h, main, one, two), single1(h, main, one, two), single2(h, main, one, two)} | moves(t, single(h, #main, one, two), single1(h, main, one, two), single2(h, main, one, two))]
	end

	#def split([], n) do
	#	
	#end

	#def split([h|t], n) do
	#	if Trainshunting.position([n|t], n) == 1 do
	#		[[]| [t]]
	#	else
	#		if h == n do
	#			split(t, n)
	#		else
	#			[[h] | split(t, n)]
#
#			end
#		end
#
#	end
	def split(xs, y) do
		{Trainshunting.take(xs, Trainshunting.position(xs, y)-1),Trainshunting.drop(xs, Trainshunting.position(xs, y))}
	end

	def find({[],[],[]},{[],[],[]}) do
		[]
	end


	def find({xs,[],[]},{[y|t],[],[]}) do
    	{hs,ts} = split(xs,y)
    	ts = [y|ts]     #Trainshunting.append(y,ts) #lägg t o y i samma lista
    	[_|tail] = Trainshunting.append(ts,hs) 
    	moves = [{:one, length(ts)}, {:two, length(hs)}, {:one, -length(ts)}, {:two, -length(hs)}]
    	Trainshunting.append([{:one, length(ts)},{:two, length(hs)}, {:one, -length(ts)}, {:two, -length(hs)}], find({tail,[],[]}, {t,[],[]}))
#[moves|find({tail, [], []}, {t, [], []})]
  	end


end


defmodule Shunt do
	
	def few({[],[],[]},{[],[],[]}) do
		[]
	end


	def few({[xs|tx],[],[]},{[y|ty],[],[]}) do

		{hs,ts} = Moves.split([xs|tx], y)
    	ts = [y|ts]   
    	[_|tail] = Trainshunting.append(ts,hs) 

		if xs == y do
			few({tx,[],[]},{ty,[],[]})
		else if xs != y do
			Trainshunting.append([{:one, length(ts)},{:two, length(hs)}, {:one, -length(ts)}, {:two, -length(hs)}], few({tail,[],[]}, {ty,[],[]}))
		end
	end

	end

	def rules([h]) do
		[h]
	end

	#def rules([{p, n}, {p2, m}|tail]) do
	#	if p == p2 do
	#		rules([{p, n + m}|tail])
	#	else
	#		[{p, n} | rules([{p2, m}|tail])]
	#	end
	#
	#	if n = 0 do
	#		rules([{p2, m}|tail])
	#	end

	def rules([{p, n}, {p2, m}|tail]) do
		cond do
			p == p2 -> rules([{p, n + m}|tail])
			n == 0  -> rules([{p2, m}|tail])
			true -> [{p, n} | rules([{p2, m}|tail])]	
		end
				
	end

		

	def compress(ms) do
    ns = rules(ms)
    case ns do
       ms -> ms
      _ -> compress(ns)
 	 end
    end


end