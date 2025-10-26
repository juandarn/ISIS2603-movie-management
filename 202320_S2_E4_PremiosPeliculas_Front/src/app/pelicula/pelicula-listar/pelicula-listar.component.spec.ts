/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { faker } from '@faker-js/faker';
import { PeliculaListarComponent } from './pelicula-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { PeliculaDTO } from '../pelicula';
import { PeliculaService } from '../pelicula.service'; 
import { PeliculaDetail } from '../peliculaDetail';
import { RouterTestingModule } from '@angular/router/testing';

describe('PeliculaListarComponent', () => {
  let component: PeliculaListarComponent;
  let fixture: ComponentFixture<PeliculaListarComponent>;
  let debug: DebugElement;
  let debugPeliculaDetail: PeliculaDetail

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ PeliculaListarComponent ],
      providers: [PeliculaService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PeliculaListarComponent);
    component = fixture.componentInstance;
    
    let testPeliculas: Array<PeliculaDetail> = [ ];
    
    for (let i = 0; i < 10; i++) {
      let pelicula = new PeliculaDetail(faker.number.int(),
      faker.lorem.words(),
      faker.number.int(), 
      faker.lorem.words(),
      faker.lorem.words(),
      faker.date.past(), 
      faker.lorem.words(),

      faker.lorem.words(),
      [],
      [],
      [],
      [],
      [],
      []
      )
      testPeliculas.push(pelicula);
      debug = fixture.debugElement;
    }
    component.peliculas = testPeliculas;
    fixture.detectChanges();
    

  });

  it('Creacion componente listar pelicula', () => {
    expect(component).toBeTruthy();
  });


    it('should have 10 <div.col-2.mb-3.mt-3> elements', () => {
      expect(debug.queryAll(By.css('div.col-2.mb-3.mt-3'))).toHaveSize(10)
    });
   
    it('should have 10 <card.> elements ', () => {
      expect(debug.queryAll(By.css('div.card'))).toHaveSize(10)
    });
   
  
    it('should have 10 <div.card-front> elements', () => {
      expect(debug.queryAll(By.css('div.card-front'))).toHaveSize(10)
    });

    it('should have 10 <img> elements', () => {
      expect(debug.queryAll(By.css('img'))).toHaveSize(10)
    });

    it('should have h5 tag with the pelicula.name', () => {
      debug.queryAll(By.css('h5.card-title')).forEach((h5, i)=>{
        expect(h5.nativeElement.textContent).toContain(component.peliculas[i].nombre)
      });
    });

    it('should have h5 tag with the pelicula.name', () => {
      debug.queryAll(By.css('h5.card-title')).forEach((h5, i)=>{
        expect(h5.nativeElement.textContent).toContain(component.peliculas[i].nombre)
      });
    });

  

    it('should have p5 tag with the pelicula.duracion', () => {
      debug.queryAll(By.css('p.card-text')).forEach((p, i)=>{
        expect(p.nativeElement.textContent).toContain(component.peliculas[i].duracion)
      });
    });

    it('should have 9 <div.col.mb-2> elements and the deleted pelicula should not exist', () => {
      const pelicula = component.peliculas.pop()!;
      fixture.detectChanges();
      expect(debug.queryAll(By.css('div.col-2.mb-3.mt-3'))).toHaveSize(9)
   
      debug.queryAll(By.css('div.col-2.mb-2')).forEach((selector, i)=>{
        expect(selector.nativeElement.textContent).not.toContain(pelicula.nombre);
      });
    });
});