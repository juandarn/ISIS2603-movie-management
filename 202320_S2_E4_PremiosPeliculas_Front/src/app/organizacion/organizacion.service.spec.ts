/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { OrganizacionService } from './organizacion.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('Service: Organizacion', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OrganizacionService]
    });
  });

  it('should ...', inject([OrganizacionService], (service: OrganizacionService) => {
    expect(service).toBeTruthy();
  }));
});
