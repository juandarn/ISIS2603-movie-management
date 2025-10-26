import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { PeliculaDTO } from '../pelicula';
import { Router } from '@angular/router';
import { PeliculaService } from '../pelicula.service';

@Component({
  selector: 'app-pelicula-create',
  templateUrl: './pelicula-crear.component.html',
  styleUrls: ['./pelicula-crear.component.css'],
})
export class PeliculaCrearComponent implements OnInit {
  peliculaForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private peliculaService: PeliculaService,
    private router: Router
  ) {}

  createPelicula(pelicula: PeliculaDTO) {
    if (!this.peliculaForm.valid) return;
    const date = this.peliculaForm.controls['fechaEstreno'].value;
    const formattedDate: Date = new Date(date);
    pelicula.fechaEstreno = formattedDate;
    this.peliculaService.createPelicula(pelicula).subscribe((apiData) => {
      console.info('La pelicula fue creada: ', apiData);
      this.toastr.success('Confirmation', 'Pelicula creada');
      this.router.navigate(['/peliculas/list']);
      this.peliculaForm.reset();
    });
  }

    cancelCreation() {
      this.toastr.warning("La pelicula no fue creada", 'Creación de pelicula');
      this.peliculaForm.reset();
    }
  
    ngOnInit() {
      this.peliculaForm = this.formBuilder.group({
        nombre: ['', [Validators.required, Validators.minLength(1)]],
        duracion: ['', Validators.required],
        pais: ['', [Validators.required, Validators.minLength(4)]],
        idiomaOriginal: ['', [Validators.required]],
        fechaEstreno: ['', Validators.required],
        linkTrailer: ['', Validators.required],
        imagen: ['', Validators.required],
      });
    }
  
  

  }

  

