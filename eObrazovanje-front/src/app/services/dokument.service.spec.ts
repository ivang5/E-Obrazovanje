import { TestBed } from '@angular/core/testing';

import { DokumentService } from './dokument.service';

describe('DokumentService', () => {
  let service: DokumentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DokumentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
