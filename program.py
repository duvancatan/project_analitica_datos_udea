def fibonacci(n):
    """Devuelve una lista con los primeros n términos de la sucesión de Fibonacci."""
    if n <= 0:
        return []
    seq = [0, 1]
    while len(seq) < n:
        seq.append(seq[-1] + seq[-2])
    return seq[:n]

# Ejemplo de uso
if __name__ == "__main__":
    cantidad = 20
    print(f"Primeros {cantidad} términos de Fibonacci:", fibonacci(cantidad))