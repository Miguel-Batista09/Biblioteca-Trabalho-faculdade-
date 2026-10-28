package br.edu.biblioteca.controller;

import br.edu.biblioteca.dao.LivroDao;
import br.edu.biblioteca.modelo.Livro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet dedicado às operações de CRUD da entidade Livro (Trabalho Prático 03).
 *
 * Assim como o BibliotecaController, este servlet NÃO é mapeado em "/":
 * arquivos estáticos (como /css/estilos.css) continuam sendo servidos
 * pelo "default servlet" do Tomcat, sem interferência.
 */
@WebServlet(name = "LivroServlet", urlPatterns = {"/livros"})
public class LivroServlet extends HttpServlet {

    private transient LivroDao dao;

    @Override
    public void init() throws ServletException {
        this.dao = new LivroDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");

        // Parâmetro "acao" define a operação: /livros?acao=listar, /livros?acao=novo etc.
        String acao = request.getParameter("acao");
        if (acao == null || acao.isBlank()) {
            acao = "listar";
        }

        switch (acao) {
            case "novo":
                mostrarFormulario(request, response);
                break;
            case "adicionar":
                adicionar(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            case "atualizar":
                atualizar(request, response);
                break;
            case "excluir":
                excluir(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
                break;
        }
    }

    /** Lista todos os livros (ou apenas os de uma categoria, se informada). */
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Filtro por categoria (botões de categoria da página): /livros?categoria=Economia
        String categoria = request.getParameter("categoria");
        if (categoria == null || categoria.isBlank()) {
            request.setAttribute("livros", dao.getLista());
        } else {
            request.setAttribute("livros", dao.getListaPorCategoria(categoria));
        }
        request.setAttribute("categoriaAtual", categoria);
        request.getRequestDispatcher("/WEB-INF/views/lista-livros.jsp")
                .forward(request, response);
    }

    /** Exibe o formulário vazio para cadastro de um novo livro. */
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/formulario-livro.jsp")
                .forward(request, response);
    }

    /** Insere um novo livro e redireciona para a listagem. */
    private void adicionar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");

        Livro livro = preencherLivro(new Livro(), request);
        dao.adiciona(livro);

        // Redirect evita re-submissão do formulário (padrão Post/Redirect/Get)
        response.sendRedirect("livros?acao=listar");
    }

    /** Carrega o livro pelo id e abre o formulário em modo edição. */
    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        request.setAttribute("livro", dao.buscaPorId(id));
        request.getRequestDispatcher("/WEB-INF/views/formulario-livro.jsp")
                .forward(request, response);
    }

    /** Atualiza um livro existente e redireciona para a listagem. */
    private void atualizar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");

        Livro livro = new Livro();
        livro.setId(Long.parseLong(request.getParameter("id")));
        preencherLivro(livro, request);
        dao.altera(livro);

        response.sendRedirect("livros?acao=listar");
    }

    /** Remove um livro pelo id e redireciona para a listagem. */
    private void excluir(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.remove(id);
        response.sendRedirect("livros?acao=listar");
    }

    /** Copia os parâmetros HTTP do formulário para o objeto Livro. */
    private Livro preencherLivro(Livro livro, HttpServletRequest request) {
        livro.setTitulo(request.getParameter("titulo"));
        livro.setAutor(request.getParameter("autor"));
        livro.setCategoria(request.getParameter("categoria"));

        String anoTexto = request.getParameter("anoPublicacao");
        livro.setAnoPublicacao(
                (anoTexto == null || anoTexto.isBlank()) ? 0 : Integer.parseInt(anoTexto));

        livro.setDescricao(request.getParameter("descricao"));
        return livro;
    }
}
