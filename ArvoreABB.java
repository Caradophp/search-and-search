import java.util.List;
import java.util.ArrayList;

public class ArvoreABB {

    private NoABB raiz;
    private int quant;

    public ArvoreABB() {
        this.raiz = null;
        this.quant = 0;
    }

    public NoABB getRaiz() {
        return raiz;
    }

    public int getQuant() {
        return quant;
    }

    public NoABB pesquisar(String nome) {
        return pesquisar(nome, this.raiz);
    }

    private NoABB pesquisar(String nome, NoABB no) {

        if (no == null) {
            return null;
        }

        int comparacao = nome.compareToIgnoreCase(
                no.getEmprestimos().get(0).getNomeUsuario()
        );

        if (comparacao == 0) {
            return no;
        }

        if (comparacao < 0) {
            return pesquisar(nome, no.getEsq());
        }

        return pesquisar(nome, no.getDir());
    }

    public void inserir(Emprestimo emprestimo) {
        this.raiz = inserir(emprestimo, this.raiz);
    }

    private NoABB inserir(Emprestimo emprestimo, NoABB no) {

        if (no == null) {

            List<Emprestimo> lista = new ArrayList<>();
            lista.add(emprestimo);

            this.quant++;

            return new NoABB(lista);
        }

        String nomeNovo = emprestimo.getNomeUsuario();

        String nomeAtual = no.getEmprestimos()
                .get(0)
                .getNomeUsuario();

        int comparacao = nomeNovo.compareToIgnoreCase(nomeAtual);

        if (comparacao == 0) {
            no.getEmprestimos().add(emprestimo);
        } else if (comparacao < 0) {
            no.setEsq(inserir(emprestimo, no.getEsq()));
        } else {
            no.setDir(inserir(emprestimo, no.getDir()));
        }

        return no;
    }
}
