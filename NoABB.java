import java.util.List;

public class NoABB {

    private List<Emprestimo> emprestimos;
    private NoABB esq, dir;

    public NoABB(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
        this.esq = null;
        this.dir = null;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimo(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public NoABB getEsq() {
        return esq;
    }

    public void setEsq(NoABB esq) {
        this.esq = esq;
    }

    public NoABB getDir() {
        return dir;
    }

    public void setDir(NoABB dir) {
        this.dir = dir;
    }
}
