import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import javax.swing.*;


// não faz sentido, precisa ser algo mais especifico.  pois as classes ( livros, revista) herdam de ItemdoAcervo  as caracteristicas titulo e ano, e para especificar elas precisam adicionar mais duas caracteristicas(parametros), que no caso do livro é o autor, e no caso da revista é a edicao. entao sempre é melhor especificar, para que nao haja erro
public class Biblioteca{
    public String AutorDoLivro;
    private List<ItemDoAcervo> acervo;
    private List<Usuario> listaUsuarios;
    private List<Emprestimo> registroDeEmprestimos;



    public Biblioteca(){

        this.acervo = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.registroDeEmprestimos = new ArrayList<>();
    }


    public void realizarEmprestimo(String UserUsuario, String titulo) {
        Usuario usuarioDoEmprestimo = pesquisarUsuarioPorUser(UserUsuario);
        if (usuarioDoEmprestimo == null) {
            System.out.println("Erro: esse usuário não está cadastrado.");
            return;
        }
        ItemDoAcervo livroDoEmprestimo = pesquisarItemPorTitulo(titulo);
        if (livroDoEmprestimo == null) {
            System.out.println("Erro: esse livro não está cadastrado.");
            return;
        }
        if (livroDoEmprestimo.getStatus() == StatusLivro.EMPRESTADO) {
            System.out.println("Erro: esse livro já está emprestado.");
            return;
        }

        LocalDate hoje = LocalDate.now();
        LocalDate devolucaoPrevista = hoje.plusDays(livroDoEmprestimo.getPrazo());

        livroDoEmprestimo.setStatus(StatusLivro.EMPRESTADO);
        Emprestimo emprestimo = new Emprestimo(livroDoEmprestimo, usuarioDoEmprestimo, hoje, devolucaoPrevista );
        registroDeEmprestimos.add(emprestimo);
        System.out.println("Emprestimo cadastrado com sucesso.");
        System.out.println( "O livro " + livroDoEmprestimo.getTitulo() + " foi emprestado para o usuario " + usuarioDoEmprestimo.getName() + " na data " + emprestimo.getDataEmprestimo() + " e a data de devolução é " + emprestimo.getDataDevolucaoPrevista() );
    }
    public void cadastrarItem(ItemDoAcervo item){
        this.acervo.add(item);
        System.out.println("Livro "+ item.getTitulo() + " adicionado no acervo");
    }

    public void cadastrarUsuario(Usuario usuario){
        listaUsuarios.add(usuario);
        System.out.println("Usuario "+ usuario.getUser() + " adicionado no acervo");
    }

    public ItemDoAcervo pesquisarItemPorTitulo(String titulo){
        for ( ItemDoAcervo item : this.acervo){
            if(item.getTitulo().equalsIgnoreCase(titulo)) {
                return item ;
            }
        }
        return null;
    }

    public Usuario pesquisarUsuarioPorUser(String User) {
        for(Usuario usuario : this.listaUsuarios) {
            if(usuario.getUser().equals(User)) {
                return usuario;
            }
        }
        return null;
    }
    public List<ItemDoAcervo> pesquisarItemPorTermo(String termo) {
        List<ItemDoAcervo> listaDeLivros = new ArrayList<>();
        for (var item : acervo) {
            if(item.getTitulo().toLowerCase().contains(termo.toLowerCase())) {
                listaDeLivros.add(item);
            }
        }
        return listaDeLivros;
    }

    public void listarAcervo() {
        System.out.println("Livros no Acervo");
        for (ItemDoAcervo item : acervo) {
            System.out.println(item);
        }
    }

    private Emprestimo buscarEmprestimoAtivoPorItem(ItemDoAcervo item){
        for (var emprestimo : registroDeEmprestimos) {
            if(emprestimo.getItem().getTitulo().equalsIgnoreCase(item.getTitulo())) {
                System.out.println(item.getStatus());
                return emprestimo;
            }
        }
        return null;
    }

    public void realizarDevolucao(String titulo) {
        ItemDoAcervo item = pesquisarItemPorTitulo(titulo);
        if(item == null) {
            System.out.println("Erro: esse item não está cadastrado.");
            return;
        }
        Emprestimo emprestimo = buscarEmprestimoAtivoPorItem(item);
        if(emprestimo == null) {
            System.out.println("Erro: esse emprestimo não existe.");
            return;
        }
        LocalDate hoje = LocalDate.now().plusDays(8); // 3 dias de prazo + 5 dias de atraso, por isso o numero 8.
        long dias = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), hoje);

        if(dias > 0) {
            double multa = dias * item.getValorMultaPorDiaAtraso();
            System.out.println("Item devolvido. Você precisa pagar uma multa de R$" + multa);
        } else {
            System.out.println("Item devolvido.");
        }
        emprestimo.getItem().setStatus(StatusLivro.DISPONIVEL);
        emprestimo.setDataDevolucaoPrevista(hoje);
    }

    public List<ItemDoAcervo> buscar(String termo) {

        List<ItemDoAcervo> resultados = new ArrayList<>();

        String termoMinusculo = termo.toLowerCase();

        for (ItemDoAcervo item : acervo) {

            boolean tituloContem = item.getTitulo().toLowerCase().contains(termoMinusculo);


            boolean autorContem = false;
            if (item instanceof Livro) {
                Livro livro = (Livro) item;
                autorContem = livro.getAutor().toLowerCase().contains(termoMinusculo);
            }


            if (tituloContem || autorContem) {
                resultados.add(item);
            }
        }
        return resultados;

    }



    public static void main(String[] args) {
        Livro livroJavaComoProgramar = new Livro("Java Como Programar", "Deitel", 2014);
        Livro livroMemoria = new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881);
        Usuario meuUsuario = new Usuario("Thiago", "Thiago Carvalho", "123");
        Biblioteca minhaBiblioteca = new Biblioteca();
        minhaBiblioteca.cadastrarItem(livroJavaComoProgramar);
        minhaBiblioteca.cadastrarItem(livroMemoria);
        minhaBiblioteca.cadastrarUsuario(meuUsuario);
        minhaBiblioteca.listarAcervo();
        minhaBiblioteca.realizarEmprestimo("Thiago", "Memórias Póstumas de Brás Cubas");
        minhaBiblioteca.buscarEmprestimoAtivoPorItem(livroMemoria);
        Revista revistaveja = new Revista("veja",2024 ,1 );
        System.out.println(revistaveja);
        System.out.println(livroJavaComoProgramar);
        DVD Barbie = new DVD("Barbie" ,2025,60);
        minhaBiblioteca.cadastrarItem(Barbie);
        minhaBiblioteca.listarAcervo();
        minhaBiblioteca.realizarEmprestimo("Thiago", "Barbie");
        minhaBiblioteca.realizarDevolucao("Barbie");
        minhaBiblioteca.listarAcervo();
        Livro Tolkien = new Livro("Tolkien", "thiago", 2014);
        minhaBiblioteca.cadastrarItem(Tolkien);
        Livro livroSenhorDosAneis = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954);
        Livro livroHobbit = new Livro("O Hobbit", "J.R.R. Tolkien", 1937);
        Livro livroJavaAvancado = new Livro("Java Avançado", "Deitel", 2018);
        minhaBiblioteca.cadastrarItem(livroJavaAvancado);
        minhaBiblioteca.cadastrarItem(livroHobbit);
        minhaBiblioteca.cadastrarItem(livroSenhorDosAneis);
        List<ItemDoAcervo> resultadosTolkien = minhaBiblioteca.buscar("Tolkien");

        System.out.println("--------------------------");
        for (ItemDoAcervo item : resultadosTolkien) {
            System.out.println(item);
        }








    }
}

