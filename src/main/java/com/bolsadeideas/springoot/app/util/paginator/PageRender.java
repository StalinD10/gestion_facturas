package com.bolsadeideas.springoot.app.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {
    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numeroElementosPorPagina;

    private int paginaActual;

    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();

        numeroElementosPorPagina = page.getSize();
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1;
        int desde, hasta;
        if (totalPaginas <= numeroElementosPorPagina) {
            desde = 1;
            hasta = totalPaginas;
        } else {
            if (paginaActual <= numeroElementosPorPagina / 2) {
                desde = 1;
                hasta = numeroElementosPorPagina;
            } else if (paginaActual >= totalPaginas - numeroElementosPorPagina / 2) {
                desde = totalPaginas - numeroElementosPorPagina + 1;
                hasta = numeroElementosPorPagina;
            } else {
                desde = paginaActual - numeroElementosPorPagina / 2;
                hasta = numeroElementosPorPagina;
            }
        }
        for (int i = 0; i < hasta; i++) {
            paginas.add(new PageItem(desde + 1, paginaActual == desde + 1));
        }
    }

    public String getUrl() {
        return url;
    }

    public Page<T> getPage() {
        return page;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getNumeroElementosPorPagina() {
        return numeroElementosPorPagina;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
}
