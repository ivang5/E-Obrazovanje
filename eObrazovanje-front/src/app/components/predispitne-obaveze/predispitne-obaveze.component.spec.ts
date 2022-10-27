import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PredispitneObavezeComponent } from './predispitne-obaveze.component';

describe('PredispitneObavezeComponent', () => {
  let component: PredispitneObavezeComponent;
  let fixture: ComponentFixture<PredispitneObavezeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PredispitneObavezeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PredispitneObavezeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
