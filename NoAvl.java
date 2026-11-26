import java.util.List;

public class NoAvl {

    private List<Emprestimo> emprestimos;
    private NoAvl esq, dir;
    private int fatorBalanceamento;

    public NoAvl(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
        this.fatorBalanceamento = 0;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public NoAvl getEsq() {
        return esq;
    }

    public void setEsq(NoAvl esq) {
        this.esq = esq;
    }

    public NoAvl getDir() {
        return dir;
    }

    public void setDir(NoAvl dir) {
        this.dir = dir;
    }

    public int getFatorBalanceamento() {
        return fatorBalanceamento;
    }

    public void setFatorBalanceamento(int fatorBalanceamento) {
        this.fatorBalanceamento = fatorBalanceamento;
    }
}