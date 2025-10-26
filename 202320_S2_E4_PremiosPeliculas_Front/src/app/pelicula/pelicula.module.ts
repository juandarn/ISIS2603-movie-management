import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PeliculaComponent } from './pelicula.component';
import { PeliculaListarComponent } from './pelicula-listar/pelicula-listar.component';
import { PeliculaDetalleComponent } from './pelicula-detalle/pelicula-detalle.component';
import { PeliculaRoutingModule } from './pelicula-routing.module';
import { PeliculaCrearComponent} from './pelicula-crear/pelicula-crear.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { ReseniaModule } from "../resenia/resenia.module";

@NgModule({
    declarations: [PeliculaComponent, PeliculaListarComponent, PeliculaDetalleComponent, PeliculaCrearComponent],
    exports: [PeliculaListarComponent, PeliculaCrearComponent],
    imports: [
        CommonModule,
        RouterModule,
        PeliculaRoutingModule,
        ReactiveFormsModule,
        FormsModule,
        ReseniaModule
    ]
})
export class PeliculaModule { }
