import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component
({
  selector: 'app-labseq',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './labseq.html',
  styleUrls: ['./labseq.css']
})
export class Labseq
{
  index: number | null = null;
  result: string = '';
  error: string = '';

  constructor(private http: HttpClient) {}

  getSequence()
  {
    this.error = '';
    this.result = '';

    if (this.index === null || this.index < 0 || !Number.isInteger(this.index))
    {
      this.error = 'Please enter a non-negative index value.';
      return;
    }

    if (this.index > 100000)
    {
      this.error = 'Please enter an index value that does not exceed 100000.';
      return;
    }

    this.http.get(`/labseq/${this.index}`, { responseType: 'text' })
      .subscribe(
      {
        next: (data) => 
        {
          this.result = this.formatResult(data);
        },
        error: (err) => 
        {
          this.error = `Error: ${err.message}`;
        }
      });
  }

  formatResult(result: string): string
  {
    if (!result)
    {
      return '';
    }

    if (result.length > 15)
    {
      return `${result.slice(0, 6)}e+${result.length - 1}`;
    }

    const num = Number(result);

    if (!Number.isNaN(num))
    {
      if (Math.abs(num) < 1e6)
      {
        return num.toLocaleString();
      }
      return num.toExponential(5);
    }

    if (result.length > 15)
    {
      return `${result.slice(0, 6)}e+${result.length - 1}`;
    }

    return result;
  }
}
