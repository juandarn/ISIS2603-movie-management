/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { CinefiloService } from './cinefilo.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';


describe('Service: Cinefilo', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers: [CinefiloService]
    });
  });

  it('should ...', inject([CinefiloService], (service: CinefiloService) => {
    expect(service).toBeTruthy();
  }));
});
