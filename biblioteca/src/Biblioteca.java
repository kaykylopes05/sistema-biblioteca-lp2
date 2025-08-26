import java.util.ArrayList;
import java.util.List;

public class Biblioteca{

    private List<Livro> acervo;
    private List<Usuario> listaUsuarios;


    public Biblioteca(){

        acervo = new ArrayList<>();
        listaUsuarios = new ArrayList<>();
    }

    public void cadastrarLivro(Livro livro){
        acervo.add(livro);
        System.out.println("Livro "+ livro.getTitulo() + " adicionado no acervo");
    }

    public void cadastrarUsuario(Usuario usuario){
        listaUsuarios.add(usuario);
        System.out.println("Usuario "+ usuario.getUser() + " adicionado no acervo");
    }

    public static void main(String[] args){
        Biblioteca biblioteca = new Biblioteca();
        Livro l = new Livro("como programar", "kinha",2024);
        Usuario kayky = new Usuario("kinha", "kayky gabriel","kayky123");
        biblioteca.cadastrarUsuario(kayky);
        biblioteca.cadastrarLivro(l);





    }

}