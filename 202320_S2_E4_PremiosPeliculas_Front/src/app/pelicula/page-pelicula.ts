import { PeliculaDetail } from "./peliculaDetail";

export class PaginatedPeliculaResponse {

    peliculas: Array<PeliculaDetail> =[];
    currentPage: number;
    totalItems: number;
    totalPages: number;

    constructor(peliculas: PeliculaDetail[], currentPage: number, totalItems: number, totalPages: number) {
        this.peliculas = peliculas;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    
  }