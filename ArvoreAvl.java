import java.util.ArrayList;
import java.util.List;

public class ArvoreAvl {

    private NoAvl raiz;
    private boolean balancear;
    private int quant;

    public ArvoreAvl() {
        this.raiz = null;
        this.quant = 0;
        this.balancear = false;
    }

    public void inserir (Emprestimo emprestimo) {
        this.raiz = this.inserir(emprestimo, this.raiz);
    }

    private NoAvl inserir (Emprestimo emprestimo, NoAvl no) {
        if (no == null) {
            List<Emprestimo> lista = new ArrayList<>();
            lista.add(emprestimo);

            this.quant++;
            this.balancear = true;

            return new NoAvl(lista);
        } else {

            int comparacao = emprestimo.getNomeUsuario().compareToIgnoreCase(no.getEmprestimos().get(0).getNomeUsuario());

            if (comparacao == 0) {
                no.getEmprestimos().add(emprestimo);
                return no;
            }
            if (comparacao < 0) {
                no.setEsq(this.inserir(emprestimo, no.getEsq()));
                no = this.balancearDir(no);
                return no;
            } else {
                no.setDir(this.inserir(emprestimo, no.getDir()));
                no = this.balancearEsq(no);
                return no;
            }
        }
    }

    private NoAvl balancearDir(NoAvl no) {
        if (this.balancear) {
            switch (no.getFatorBalanceamento()) {
                case 1:
                    no.setFatorBalanceamento(0);
                    this.balancear = false;
                    break;
                case 0:
                    no.setFatorBalanceamento(-1);
                    break;
                case -1:
                    no = this.rotacaoDireita(no);
            }
        }

        return no;
    }

    private NoAvl balancearEsq(NoAvl no) {
        if (this.balancear) {
            switch (no.getFatorBalanceamento()) {
                case -1:
                    no.setFatorBalanceamento(0);
                    this.balancear = false;
                    break;
                case 0:
                    no.setFatorBalanceamento(1);
                    break;
                case 1:
                    no = this.rotacaoEsquerda(no);
            }
        }

        return no;
    }

    private NoAvl rotacaoDireita (NoAvl no){
        NoAvl temp1, temp2;
        temp1 = no.getEsq();
        if (temp1.getFatorBalanceamento() == -1){
            no.setEsq(temp1.getDir());
            temp1.setDir(no);
            no.setFatorBalanceamento((byte)0);
            no = temp1;
        }
        else {
            temp2 = temp1.getDir();
            temp1.setDir(temp2.getEsq());
            temp2.setEsq(temp1);
            no.setEsq(temp2.getDir());
            temp2.setDir(no);

            if (temp2.getFatorBalanceamento() == -1)
                no.setFatorBalanceamento((byte)1);
            else
                no.setFatorBalanceamento((byte)0);

            if (temp2.getFatorBalanceamento() == 1)
                temp1.setFatorBalanceamento((byte)-1);
            else
                temp1.setFatorBalanceamento((byte)0);

            no = temp2;
        }
        no.setFatorBalanceamento((byte)0);
        this.balancear = false;
        return no;
    }

    private NoAvl rotacaoEsquerda (NoAvl no){
        NoAvl temp1, temp2;
        temp1 = no.getDir();
        if (temp1.getFatorBalanceamento() == 1){
            no.setDir (temp1.getEsq());
            temp1.setEsq(no);
            no.setFatorBalanceamento((byte)0);
            no = temp1;
        }
        else {
            temp2 = temp1.getEsq();
            temp1.setEsq(temp2.getDir());
            temp2.setDir(temp1);
            no.setDir(temp2.getEsq());
            temp2.setEsq(no);
            if (temp2.getFatorBalanceamento() == 1)
                no.setFatorBalanceamento((byte)-1);
            else
                no.setFatorBalanceamento((byte)0);
            if (temp2.getFatorBalanceamento() == -1)
                temp1.setFatorBalanceamento((byte)1);
            else
                temp1.setFatorBalanceamento((byte)0);
            no = temp2;
        }
        no.setFatorBalanceamento((byte)0);
        this.balancear = false;
        return no;
    }

    public NoAvl pesquisar(String nome) {
        return pesquisar(nome, this.raiz);
    }

    private NoAvl pesquisar(String nome, NoAvl no) {

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
}
