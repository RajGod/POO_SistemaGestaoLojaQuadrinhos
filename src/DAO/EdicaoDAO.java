package DAO;

import java.util.List;

import entity.Edicao;

public interface EdicaoDAO {
	public void adicionar(Edicao  e) throws DAOException;
	public void excluiTitulo(Edicao e) throws DAOException;
	public int pegarID(Edicao e) throws DAOException;
	List<Edicao> pesquisarPorTipo(String titulo) throws DAOException;
}
