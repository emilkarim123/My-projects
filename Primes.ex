defmodule Bench do
    def time() do

        Enum.map([1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000, 256_000, 512_000], &Bench.bench/1)
    end
    def bench(n) do
        :timer.tc(Primes3, :createlist, [n])
        elem(:timer.tc(Primes3, :createlist, [n]),0)
    end
end


defmodule Primes1 do

		def createlist(i) do
        	list = Enum.to_list(2..i)
        	iterate(list)
   		end

    def iterate([a]) do [a] end

    def iterate([head|tail]) do
        case remove(head, tail) do
            [] -> []
            [h|t] -> [head|iterate([h|t])]
        end

    end
    def remove(_, []) do [] end

    def remove(head, [h|t]) do
        case rem(h, head) do
            0 -> remove(head, t)
            _ -> [h|remove(head, t)]
        end
    end

end

defmodule Primes2 do
	
	def createlist(n) do
       
        list = Enum.to_list(2..n)
        iterate(list, [])
   
    end

    def iterate([head|tail], l) do
        case move(head, l) do
          true ->  
          	iterate(tail, l++[head])
        _ ->
            iterate(tail, l)
        end
    end
    def iterate([], l) do l end

    def move(head, [h|t]) do
        case rem(head, h) do
            0 -> false
            _ -> move(head, t)
        end
    endh
   
    def move(_, []) do true end
    
end

defmodule Primes3 do
	
	def createlist(n) do
       
        list = Enum.to_list(2..n)
        iterate(list, [])
   
    end

    def iterate([head|tail], l) do
        case move(head, l) do
          true ->  
          	iterate(tail, [head]++l)
        _ ->
            iterate(tail, l)
        end

    end
    def iterate([], l) do 
    	Enum.reverse(l)
     end

    def move(head, [h|t]) do
        case rem(head, h) do
            0 -> false
            _ -> move(head, t)
        end
    end
   
    def move(_, []) do true end
    

end
