function pascal_row(n::Integer)
    if n < 0
        throw(ArgumentError("n debe ser >= 0"))
    end
    row = Vector{BigInt}(undef, n+1)
    row[1] = BigInt(1)
    for k in 1:n
        row[k+1] = row[k] * BigInt(n - k + 1) ÷ BigInt(k)
    end
    return row
end

# Ejemplo de uso
println(pascal_row(5))  # salida: [1, 5, 10, 10, 5, 1]