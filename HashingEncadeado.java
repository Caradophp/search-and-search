import java.util.LinkedList;
import java.util.List;

public class HashingEncadeado {

    private LinkedList<Emprestimo>[] tabela;
    private int tamanho;

    @SuppressWarnings("unchecked")
    public HashingEncadeado(int tamanho) {
        this.tamanho = tamanho;
        tabela = new LinkedList[tamanho];

        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int funcaoHash(String nome) {
        return Math.floorMod(myHashCode(nome.toLowerCase()), tamanho);
    }

    public void inserir(Emprestimo emprestimo) {
        int indice = funcaoHash(emprestimo.getNomeUsuario());

        tabela[indice].add(emprestimo);
    }

    public List<Emprestimo> pesquisar(String nome) {
        int indice = funcaoHash(nome);

        List<Emprestimo> resultados = new LinkedList<>();

        for (Emprestimo emprestimo : tabela[indice]) {

            if (emprestimo.getNomeUsuario()
                    .equalsIgnoreCase(nome)) {

                resultados.add(emprestimo);
            }
        }

        return resultados;
    }

    private int myHashCode(String texto) {
        int soma = 0;

        for (int i = 0; i < texto.length(); i++) {
            soma += texto.charAt(i);
        }

        return soma;
    }
}
