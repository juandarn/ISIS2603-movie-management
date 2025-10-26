import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { PremiacionListarComponent } from './premiacion/premiacion-listar/premiacion-listar.component';
import { PremioListarComponent } from './premio/premio-listar/premio-listar.component';
import { OrganizacionListarComponent } from './organizacion/organizacion-listar/organizacion-listar.component';
import { GeneroListarComponent } from './genero/genero-listar/genero-listar.component';
import { PeliculaListarComponent } from './pelicula/pelicula-listar/pelicula-listar.component';
import { GanadorListarComponent } from './ganador/ganador-listar/ganador-listar.component';
import { DirectorListarComponent } from './persona/director-listar/director-listar.component';
import { ActorListarComponent } from './persona/actor-listar/actor-listar.component';
describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [AppComponent, PremiacionListarComponent, PremioListarComponent, 
        OrganizacionListarComponent,DirectorListarComponent, ActorListarComponent, GeneroListarComponent,
        PeliculaListarComponent, GanadorListarComponent],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'base-project'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('base-project');
  });

  // it('should render title', () => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   fixture.detectChanges();
  //   const compiled = fixture.nativeElement as HTMLElement;
  //   expect(compiled.querySelector('.content span')?.textContent).toContain(
  //     'base-project app is running!'
  //   );
  // });
});
