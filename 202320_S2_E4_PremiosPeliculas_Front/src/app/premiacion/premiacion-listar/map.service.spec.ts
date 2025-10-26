/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { MapService } from './map.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';


describe('Service: Map', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MapService]
    });
  });

  it('should ...', inject([MapService], (service: MapService) => {
    expect(service).toBeTruthy();
  }));
});
