/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PlataformaService } from './plataforma.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Plataforma', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PlataformaService]
    });
  });

  it('should ...', inject([PlataformaService], (service: PlataformaService) => {
    expect(service).toBeTruthy();
  }));
});
