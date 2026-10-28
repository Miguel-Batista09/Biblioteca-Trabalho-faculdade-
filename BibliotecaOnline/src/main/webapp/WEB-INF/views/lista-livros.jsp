<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Livros - Biblioteca</title>
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body>
<div class="container">
    <h1>📚 Biblioteca Online</h1>
    <p class="subtitle">Seu acervo digital de conhecimento</p>

    <div class="categorias">
        <strong>Categorias disponíveis:</strong> Economia, Empreendedorismo, Psicologia, Administração, Direito, Sociologia, Teologia.
    </div>

    <a href="new" class="btn btn-primary">+ Cadastrar Novo Livro</a>

    <div class="table-container">
        <c:choose>
            <c:when test="${empty livros}">
                <div class="empty-message">
                    <p>Nenhum livro cadastrado ainda.</p>
                    <p>Clique no botão acima para adicionar o primeiro livro!</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Categoria</th>
                        <th>Ano</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="livro" items="${livros}">
                        <tr>
                            <td><strong>${livro.titulo}</strong></td>
                            <td>${livro.autor}</td>
                            <td>${livro.categoria}</td>
                            <td>${livro.anoPublicacao}</td>
                            <td>
                                <div class="actions">
                                    <a href="edit?id=${livro.id}" class="btn btn-secondary btn-small">Editar</a>
                                    <a href="delete?id=${livro.id}" class="btn btn-danger btn-small" onclick="return confirm('Tem certeza que deseja excluir este livro?')">Excluir</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>