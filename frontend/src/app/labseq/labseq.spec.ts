import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Labseq } from './labseq';

describe('Labseq', () => {
  let component: Labseq;
  let fixture: ComponentFixture<Labseq>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Labseq]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Labseq);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
