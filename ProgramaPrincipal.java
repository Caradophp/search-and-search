import java.util.LinkedList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProgramaPrincipal {

    private static final int REPETICOES = 5;

    private static final String PASTA_ORIGINAIS = "arquivosOriginais/";
    private static final String PASTA_GERADOS = "arquivosGerados/";

    private static final String[] arquivos = {
            "emprestimo1000alea.txt",
            "emprestimo1000ord.txt",
            "emprestimo1000inv.txt",
            "emprestimo5000alea.txt",
            "emprestimo5000ord.txt",
            "emprestimo5000inv.txt",
            "emprestimo10000alea.txt",
            "emprestimo10000ord.txt",
            "emprestimo10000inv.txt",
            "emprestimo50000alea.txt",
            "emprestimo50000ord.txt",
            "emprestimo50000inv.txt"
        };

    public static void main(String[] args) {

        criarPastas();

        for (String nomeArquivo : arquivos) {
            String caminhoArquivo = PASTA_ORIGINAIS + nomeArquivo;
            String identificacao = nomeArquivo.replace(".txt", "");

            System.out.println("\n========================================");
            System.out.println("ARQUIVO: " + nomeArquivo);
            System.out.println("========================================");

            executarHeapsort(caminhoArquivo, identificacao);
            executarQuicksort(caminhoArquivo, identificacao);
            executarQuicksortInsercao(caminhoArquivo, identificacao);
            executarABB(caminhoArquivo, identificacao);
            executarAVL(caminhoArquivo, identificacao);
            executarHashing(caminhoArquivo, identificacao);
        }

        System.out.println("\n========================================");
        System.out.println("TODOS OS TESTES FORAM FINALIZADOS!");
        System.out.println("Arquivos disponíveis em: " + PASTA_GERADOS);
        System.out.println("========================================");
    }

    // =====================================
    // CRIAÇÃO DAS PASTAS
    // =====================================

    private static void criarPastas() {
        try {
            Files.createDirectories(Paths.get(PASTA_ORIGINAIS));
            Files.createDirectories(Paths.get(PASTA_GERADOS));
        } catch (Exception e) {
            System.out.println("Erro ao criar pastas: " + e.getMessage());
        }
    }

    // =====================================
    // HEAPSORT
    // =====================================

    private static void executarHeapsort(String arquivo, String identificacao) {
        double tempoTotal = 0;

        List<Emprestimo> emprestimos = null;

        for (int i = 0; i < REPETICOES; i++) {
            emprestimos = Util.carregarEmprestimos(arquivo);
            long inicio = System.nanoTime();
            ordenacaoHeapsort(emprestimos);
            long fim = System.nanoTime();
            tempoTotal += (fim - inicio);
        }

        double tempoMedio = tempoTotal / REPETICOES;

        String saida = PASTA_GERADOS + "Heap_" + identificacao + ".txt";

        Util.escreveArquivo(emprestimos, saida);

        System.out.printf("Heapsort: %.4f ms%n", tempoMedio / 1_000_000.0);
    }

    private static void ordenacaoHeapsort(List<Emprestimo> emprestimos) {

        int dir = emprestimos.size() - 1;
        int esq = (dir - 1) / 2;

        while (esq >= 0) {
            refazheap(dir, esq, emprestimos);
            esq--;
        }

        while (dir > 0) {
            Emprestimo temp = emprestimos.get(0);
            emprestimos.set(0, emprestimos.get(dir));
            emprestimos.set(dir, temp);
            dir--;
            refazheap(dir, 0, emprestimos);
        }
    }

    private static void refazheap(int dir, int esq, List<Emprestimo> emprestimos) {
        int i = esq;
        int mF = 2 * i + 1;

        Emprestimo raiz = emprestimos.get(i);

        boolean heap = false;

        while (mF <= dir && !heap) {
            if (mF < dir) {
                if (comparar(emprestimos.get(mF), emprestimos.get(mF + 1)) < 0) {
                    mF++;
                }
            }

            if (comparar(raiz, emprestimos.get(mF)) < 0) {
                emprestimos.set(i, emprestimos.get(mF));
                i = mF;
                mF = 2 * i + 1;
            } else {
                heap = true;
            }
        }

        emprestimos.set(i, raiz);
    }

    // =====================================
    // QUICKSORT
    // =====================================

    private static void executarQuicksort(String arquivo, String identificacao) {

        double tempoTotal = 0;

        List<Emprestimo> emprestimos = null;

        for (int i = 0; i < REPETICOES; i++) {
            emprestimos = Util.carregarEmprestimos(arquivo);
            long inicio = System.nanoTime();
            ordenacaoQuicksort(emprestimos);
            long fim = System.nanoTime();
            tempoTotal += fim - inicio;
        }

        String saida = PASTA_GERADOS + "Quick_" + identificacao + ".txt";

        Util.escreveArquivo(emprestimos, saida);

        System.out.printf("Quicksort: %.4f ms%n", tempoTotal / REPETICOES / 1_000_000.0);
    }

    private static void ordenacaoQuicksort(List<Emprestimo> emprestimos) {
        orderna(0, emprestimos.size() - 1, emprestimos, false);
    }

    // =====================================
    // QUICKSORT + INSERÇÃO DIRETA
    // =====================================

    private static void executarQuicksortInsercao(String arquivo, String identificacao) {
        double tempoTotal = 0;

        List<Emprestimo> emprestimos = null;

        for (int i = 0; i < REPETICOES; i++) {
            emprestimos = Util.carregarEmprestimos(arquivo);
            long inicio = System.nanoTime();
            ordenacaoQuicksortInsercaoDireta(emprestimos);
            long fim = System.nanoTime();
            tempoTotal += fim - inicio;
        }

        String saida = PASTA_GERADOS + "QuickIns_" + identificacao + ".txt";

        Util.escreveArquivo(emprestimos, saida);

        System.out.printf("Quicksort + Inserção: %.4f ms%n", tempoTotal / REPETICOES / 1_000_000.0);
    }

    private static void ordenacaoQuicksortInsercaoDireta(List<Emprestimo> emprestimos) {
        orderna(0, emprestimos.size() - 1, emprestimos, true);
    }

    private static void orderna(int esq, int dir, List<Emprestimo> emprestimos, boolean usaInsercaoDireta) {
        if (esq >= dir) {
            return;
        }

        if (usaInsercaoDireta && dir - esq + 1 <= 20) {
            insercaoDireta(emprestimos, esq, dir);
            return;
        }

        int i = esq;
        int j = dir;

        Emprestimo pivo = emprestimos.get((i + j) / 2);

        do {
            while (comparar(emprestimos.get(i), pivo) < 0) {
                i++;
            }
            while (comparar(emprestimos.get(j), pivo) > 0) {
                j--;
            }
            if (i <= j) {
                Emprestimo temp = emprestimos.get(i);
                emprestimos.set(i, emprestimos.get(j));
                emprestimos.set(j, temp);
                i++;
                j--;
            }
        } while (i <= j);

        if (esq < j) {
            orderna(esq, j, emprestimos, usaInsercaoDireta);
        }

        if (i < dir) {
            orderna(i, dir, emprestimos, usaInsercaoDireta);
        }
    }

    // =====================================
    // INSERÇÃO DIRETA
    // =====================================

    private static void insercaoDireta(List<Emprestimo> emprestimos, int esq, int dir) {

        for (int i = esq + 1; i <= dir; i++) {
            Emprestimo temp = emprestimos.get(i);

            int j = i - 1;

            while (j >= esq && comparar(emprestimos.get(j), temp) > 0) {
                emprestimos.set(j + 1, emprestimos.get(j));
                j--;
            }

            emprestimos.set(j + 1, temp);
        }
    }

    // =====================================
    // PESQUISA ABB
    // =====================================

    private static void executarABB(String arquivo, String identificacao) {

        double tempoTotal = 0;

        List<NoABB> resultados = null;
        List<String> nomes = null;

        for (int i = 0; i < REPETICOES; i++) {
            List<Emprestimo> emprestimos = Util.carregarEmprestimos(arquivo);
            nomes = Util.carregaNomesPesquisa();
            long inicio = System.nanoTime();

            ArvoreABB arvoreABB = new ArvoreABB();
            for (Emprestimo emprestimo : emprestimos) {
                arvoreABB.inserir(emprestimo);
            }
            resultados = new LinkedList<>();

            for (String nome : nomes) {
                NoABB pesquisado = arvoreABB.pesquisar(nome);
                resultados.add(pesquisado);
            }

            long fim = System.nanoTime();
            tempoTotal += fim - inicio;
        }

        String saida = PASTA_GERADOS + "ABB_" + identificacao + ".txt";

        Util.gerarArquivoPesquisaABB(resultados, nomes, saida);

        System.out.printf("ABB: %.4f ms%n", tempoTotal / REPETICOES / 1_000_000.0);
    }

    // =====================================
    // PESQUISA AVL
    // =====================================

    private static void executarAVL(String arquivo, String identificacao) {
        double tempoTotal = 0;

        List<NoAvl> resultados = null;
        List<String> nomes = null;

        for (int i = 0; i < REPETICOES; i++) {
            List<Emprestimo> emprestimos = Util.carregarEmprestimos(arquivo);
            nomes = Util.carregaNomesPesquisa();
            long inicio = System.nanoTime();
            ArvoreAvl arvoreAvl = new ArvoreAvl();

            for (Emprestimo emprestimo : emprestimos) {
                arvoreAvl.inserir(emprestimo);
            }

            resultados = new LinkedList<>();

            for (String nome : nomes) {
                NoAvl pesquisado = arvoreAvl.pesquisar(nome);
                resultados.add(pesquisado);
            }

            long fim = System.nanoTime();
            tempoTotal += fim - inicio;
        }

        String saida = PASTA_GERADOS + "AVL_" + identificacao + ".txt";

        Util.gerarArquivoPesquisaAvl(resultados, nomes, saida);

        System.out.printf("AVL: %.4f ms%n", tempoTotal / REPETICOES / 1_000_000.0);
    }

    // =====================================
    // PESQUISA HASHING ENCADEADO
    // =====================================

    private static void executarHashing(String arquivo, String identificacao) {
        double tempoTotal = 0;

        List<List<Emprestimo>> resultados = null;
        List<String> nomes = null;

        for (int i = 0; i < REPETICOES; i++) {
            List<Emprestimo> emprestimos = Util.carregarEmprestimos(arquivo);
            nomes = Util.carregaNomesPesquisa();
            long inicio = System.nanoTime();
            HashingEncadeado hashing = new HashingEncadeado(1000);
            for (Emprestimo emprestimo : emprestimos) {
                hashing.inserir(emprestimo);
            }

            resultados = new LinkedList<>();

            for (String nome : nomes) {
                List<Emprestimo> pesquisados = hashing.pesquisar(nome);
                resultados.add(pesquisados);
            }

            long fim = System.nanoTime();
            tempoTotal += fim - inicio;
        }

        String saida = PASTA_GERADOS + "Hash_" + identificacao + ".txt";

        Util.gerarArquivoPesquisaHashing(resultados, nomes, saida);

        System.out.printf("Hashing Encadeado: %.4f ms%n", tempoTotal / REPETICOES / 1_000_000.0);
    }

    // =====================================
    // COMPARAÇÃO DOS EMPRÉSTIMOS
    // =====================================

    private static int comparar(Emprestimo e1, Emprestimo e2) {

        int comparacaoNome = e1.getNomeUsuario().compareToIgnoreCase(e2.getNomeUsuario());

        if (comparacaoNome == 0) {
            return Integer.compare(e1.getCodigoEmprestimo(), e2.getCodigoEmprestimo());
        }

        return comparacaoNome;
    }
}
