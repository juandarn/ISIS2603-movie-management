/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DirectorListarComponent } from './director-listar.component';
import { HttpClientModule } from '@angular/common/http';
import { PersonaDTO } from '../personaDTO';
import { faker } from '@faker-js/faker';
import { PersonaService } from '../persona.service';
import { RouterTestingModule } from '@angular/router/testing';
import { PersonaDetailDTO } from '../personaDetailDTO';




describe('DirectorListarComponent', () => {
  let component: DirectorListarComponent;
  let fixture: ComponentFixture<DirectorListarComponent>;
  let debug: DebugElement;
  let debugPersonaDTO: PersonaDetailDTO

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule],
      declarations: [ DirectorListarComponent],
      providers: [PersonaService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DirectorListarComponent);
    component = fixture.componentInstance;

    let testActores: Array<PersonaDetailDTO> = [];

    for (let i = 0; i<10;i++){
      testActores[i] = new PersonaDetailDTO(
        faker.number.int(),
        faker.person.fullName(),
        faker.lorem.word(),
        faker.date.birthdate(),
        faker.person.bio(),
        "Director",
        [],
        [],
        [],
        "https://dummyimage.com/600x400/000/fff"
        );
        component.directores.push(testActores[i]);
    }


    fixture.detectChanges();
    debug = fixture.debugElement;

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('all should be directors', () => {
    for (let i = 0; i<10;i++){
      let b:boolean = component.directores[i].rol === "Director" || component.directores[i].rol === "Directora" ;
      expect(b).toEqual(true);
    }
  });


  it('should have the corresponding data to the person', () => {
    debug.queryAll(By.css('.card.DataPerson')).forEach((fila, i)=>{
      expect(fila.query(By.css('img')).attributes["src"]).toEqual(
        component.directores[i].imagen);

      expect(fila.query(By.css('h5')).nativeElement.textContent).toEqual(
        component.directores[i].nombre);
    });
  });

});
