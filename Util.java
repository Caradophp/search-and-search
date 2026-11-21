import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Util {

    public static List<Emprestimo> carregarEmprestimos(String nomeArrquivo) {
        List<Emprestimo> emprestimos = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArrquivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                Emprestimo emprestimo =
                        new Emprestimo(
                                Integer.parseInt(data[0]),
                                data[1],
                                data[2],
                                data[3],
                                LocalDate.parse(data[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                LocalDate.parse(data[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                emprestimos.add(emprestimo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emprestimos;
    }

    public static List<String> carregaNomesPesquisa() {
        List<String> nomes = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("nomes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                nomes.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nomes;
    }

    public static void escreveArquivo(List<Emprestimo> emprestimos, String nomeArquivo) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Emprestimo emprestimo : emprestimos) {
                String line = emprestimo.getCodigoEmprestimo() + ";" + emprestimo.getNomeUsuario() + ";" + emprestimo.getTituloLivro()
                        + ";" + emprestimo.getAutor() + ";" + emprestimo.getDataEmprestimo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        + ";" + emprestimo.getDataDevolucao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void gerarArquivoPesquisaABB(List<NoABB> nos, List<String> nomes, String nomeArquivo) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {

            for (int i = 0; i < nomes.size(); i++) {
                String nome = nomes.get(i);
                bw.write("NOME: " + nome);
                bw.newLine();

                NoABB resultado = nos.get(i);

                if (resultado != null) {

                    List<Emprestimo> emprestimos = resultado.getEmprestimos();

                    for (Emprestimo e : emprestimos) {
                        bw.write("Empréstimo: " + e.getCodigoEmprestimo()
                                + " - Livro: " + e.getTituloLivro()
                                + " - Autor: " + e.getAutor());
                        bw.newLine();
                        bw.write("Data do empréstimo: " + e.getDataEmprestimo()
                                + " - Data de devolução: " + e.getDataDevolucao());
                        bw.newLine();
                    }
                    bw.write("TOTAL: " + emprestimos.size() + " empréstimos");
                } else {
                    bw.write("Nenhum empréstimo encontrado.");
                }

                bw.newLine();
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gerarArquivoPesquisaAvl(List<NoAvl> nos, List<String> nomes, String nomeArquivo) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {

            for (int i = 0; i < nomes.size(); i++) {
                String nome = nomes.get(i);
                bw.write("NOME: " + nome);
                bw.newLine();

                NoAvl resultado = nos.get(i);

                if (resultado != null) {

                    List<Emprestimo> emprestimos = resultado.getEmprestimos();

                    for (Emprestimo e : emprestimos) {
                        bw.write("Empréstimo: " + e.getCodigoEmprestimo()
                                + " - Livro: " + e.getTituloLivro()
                                + " - Autor: " + e.getAutor());
                        bw.newLine();
                        bw.write("Data do empréstimo: " + e.getDataEmprestimo()
                                + " - Data de devolução: " + e.getDataDevolucao());
                        bw.newLine();
                    }
                    bw.write("TOTAL: " + emprestimos.size() + " empréstimos");
                } else {
                    bw.write("Nenhum empréstimo encontrado.");
                }

                bw.newLine();
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
