/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { GeocodeService } from './geolocalizacion.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';



describe('Service: Geolocalizacion', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [GeocodeService]
    });
  });

  it('should ...', inject([GeocodeService], (service: GeocodeService) => {
    expect(service).toBeTruthy();
  }));
});
