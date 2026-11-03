import java.util.List;

public class ProgramaPrincipal {
    public static void main(String[] args) {
        List<Emprestimo> emprestimos = Util.carregarEmprestimos("emprestimo1000alea.txt");
        List<String> nomesPesquisa = Util.carregaNomesPesquisa();

        ordenacaoHeapsort(emprestimos);

        //Escreve arquivo ordenado pelo nome
        Util.escreveArquivo(emprestimos, "ordenaçãoHeap.txt");

    }

    ///////////////////////////////////////
    ///           HEAPSORT             ///
    /////////////////////////////////////

    private static void ordenacaoHeapsort(List<Emprestimo> emprestimos) {
        int dir = emprestimos.size() - 1, esq = (dir-1)/2;
        Emprestimo temp;

        while (esq >= 0) {
            refazheap(esq, emprestimos.size() - 1, emprestimos);
            esq--;
        }

        while (dir > 0) {
            temp = emprestimos.get(0);
            emprestimos.set(0, emprestimos.get(dir));
            emprestimos.set(dir, temp);
            dir--;
            refazheap(dir, 0, emprestimos);
        }
    }

    private static void refazheap(int dir, int esq, List<Emprestimo> emprestimos) {
        int i = esq, mF = 2 * i + 1;
        Emprestimo raiz = emprestimos.get(i);
        boolean heap = false;

        while ((mF <= dir) && (!heap)) {
            if (mF < dir) {
                if (comparar(emprestimos.get(mF), emprestimos.get(mF + 1)) < 0)
                    mF++;
            }
            if (comparar(raiz, emprestimos.get(mF)) < 0) {
                emprestimos.set(i, emprestimos.get(mF));
                i = mF;
                mF = 2 * i + 1;
            } else {
                heap = true;
            }
            emprestimos.set(i, raiz);
        }
    }

    ///////////////////////////////////////
    ///           QUICKSORT            ///
    /////////////////////////////////////

    private static void ordenacaoQuicksort(List<Emprestimo> emprestimos) {
        orderna(0, emprestimos.size() - 1, emprestimos, false);
    }

    private static void ordenacaoQuicksortInsercaoDireta(List<Emprestimo> emprestimos) {
        orderna(0, emprestimos.size() - 1, emprestimos, true);
    }

    private static void orderna(int esq, int dir, List<Emprestimo> emprestimos, boolean usaInsercaoDireta) {
        int i = esq, j = dir;
        Emprestimo pivo, temp;

        if (usaInsercaoDireta && emprestimos.size() <= 20) {
            insercaoDireta(emprestimos);
            return;
        }

        pivo = emprestimos.get((i+j)/2);

        do {
            while (comparar(emprestimos.get(i), pivo) < 0)
                i++;

            while (comparar(emprestimos.get(j), pivo) > 0)
                j--;


            if (i <= j) {
                temp = emprestimos.get(i);
                emprestimos.set(i, emprestimos.get(j));
                emprestimos.set(j, temp);
                i++;
                j--;
            }
        } while (i <= j);
        if (esq < j)
            orderna(esq, j, emprestimos, usaInsercaoDireta);
        if (dir > i)
            orderna(i, dir, emprestimos, usaInsercaoDireta);
    }

    ///////////////////////////////////////
    ///           UTILITÁRIOS          ///
    /////////////////////////////////////

    private static int comparar(Emprestimo e1, Emprestimo e2) {
        int comparacaoNome = e1.getNomeUsuario()
                .compareToIgnoreCase(e2.getNomeUsuario());

        if (comparacaoNome == 0) {
            return Integer.compare(e1.getCodigoEmprestimo(), e2.getCodigoEmprestimo());
        }

        return comparacaoNome;
    }

    private static void insercaoDireta(List<Emprestimo> emprestimos) {
        Emprestimo temp;
        for (int i = 1; i < emprestimos.size(); i++) {
            temp = emprestimos.get(i);
            int j = i - 1;
            while ((j >= 0) && (emprestimos.get(j).getNomeUsuario().compareToIgnoreCase(temp.getNomeUsuario()) > 0)) {
                emprestimos.set(j + 1, emprestimos.get(j));
                j--;
            }
            emprestimos.set(j + 1, temp);
        }
    }

}
