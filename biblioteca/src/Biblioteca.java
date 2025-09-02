import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import javax.swing.*;

public class Biblioteca{

    private List<Livro> acervo;
    private List<Usuario> listaUsuarios;
    private List<Emprestimo> registroDeEmprestimos;
    private static final int PRAZO_EMPRESTIMOS_DIAS = 14;
    private static final double VALOR_MULTA_POR_DIA = 0.75;


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
        Livro livroDoEmprestimo = pesquisarLivroPorTitulo(titulo);
        if (livroDoEmprestimo == null) {
            System.out.println("Erro: esse livro não está cadastrado.");
            return;
        }
        if (livroDoEmprestimo.getStatus() == StatusLivro.EMPRESTADO) {
            System.out.println("Erro: esse livro já está emprestado.");
            return;
        }

        LocalDate hoje = LocalDate.now();
        LocalDate devolucaoPrevista = hoje.plusDays(PRAZO_EMPRESTIMOS_DIAS);

        livroDoEmprestimo.setStatus(StatusLivro.EMPRESTADO);
        Emprestimo emprestimo = new Emprestimo(livroDoEmprestimo, usuarioDoEmprestimo, hoje, devolucaoPrevista );
        registroDeEmprestimos.add(emprestimo);
        System.out.println("Emprestimo cadastrado com sucesso.");
        System.out.println( "O livro " + livroDoEmprestimo.getTitulo() + " foi emprestado para o usuario " + usuarioDoEmprestimo.getName() + " na data " + emprestimo.getDataEmprestimo() + " e a data de devolução é " + emprestimo.getDataDevolucaoPrevista() );
    }
    public void cadastrarLivro(Livro livro){
        acervo.add(livro);
        System.out.println("Livro "+ livro.getTitulo() + " adicionado no acervo");
    }

    public void cadastrarUsuario(Usuario usuario){
        listaUsuarios.add(usuario);
        System.out.println("Usuario "+ usuario.getUser() + " adicionado no acervo");
    }

    public Livro pesquisarLivroPorTitulo(String titulo){
        for ( Livro l : this.acervo){
            if(l.getTitulo().equalsIgnoreCase(titulo)) {
                return l ;
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
    public List<Livro> pesquisarLivroPorTermo(String termo) {
        List<Livro> listaDeLivros = new ArrayList<>();
        for (var livro : acervo) {
            if(livro.getTitulo().toLowerCase().contains(termo.toLowerCase())) {
                listaDeLivros.add(livro);
            }
        }
        return listaDeLivros;
    }

    public void listarAcervo() {
        System.out.println("Livros no Acervo");
        for (Livro livro : acervo) {
            System.out.println(livro);
        }
    }

    private Emprestimo buscarEmprestimoAtivoPorLivro(Livro livro){
        for (var emprestimo : registroDeEmprestimos) {
            if(emprestimo.getLivro().getTitulo().equalsIgnoreCase(livro.getTitulo())) {
                System.out.println(livro.getStatus());
                return emprestimo;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Livro livroJavaComoProgramar = new Livro("Java Como Programar", "Deitel", 2014);
        Livro livroMemoria = new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881);
        Usuario meuUsuario = new Usuario("Thiago", "Thiago Carvalho", "123");
        Biblioteca minhaBiblioteca = new Biblioteca();
        minhaBiblioteca.cadastrarLivro(livroJavaComoProgramar);
        minhaBiblioteca.cadastrarLivro(livroMemoria);
        minhaBiblioteca.cadastrarUsuario(meuUsuario);
        minhaBiblioteca.listarAcervo();
        minhaBiblioteca.realizarEmprestimo("Thiago", "Memórias Póstumas de Brás Cubas");
        minhaBiblioteca.buscarEmprestimoAtivoPorLivro(livroMemoria);


    }
}

