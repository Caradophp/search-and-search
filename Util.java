import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

}
