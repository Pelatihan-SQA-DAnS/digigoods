#!/bin/bash

# Script to run code coverage analysis with JaCoCo
# This script will run tests and generate both HTML and XML coverage reports

echo "🧪 Running tests with JaCoCo code coverage..."
echo "================================================"

# Clean and run tests with coverage
./mvnw clean test

echo ""
echo "📊 Coverage reports generated:"
echo "  HTML Report: target/site/jacoco/index.html"
echo "  XML Report:  target/site/jacoco/jacoco.xml"
echo "  CSV Report:  target/site/jacoco/jacoco.csv"
echo ""

# Check if HTML report exists and offer to open it
if [ -f "target/site/jacoco/index.html" ]; then
    echo "✅ Coverage analysis complete!"
    echo ""
    echo "To view the HTML coverage report:"
    echo "  - Open target/site/jacoco/index.html in your browser"
    echo "  - Or run: open target/site/jacoco/index.html (on macOS)"
    echo ""
    
    # Display overall coverage summary from the CSV
    if [ -f "target/site/jacoco/jacoco.csv" ]; then
        echo "📈 Coverage Summary:"
        echo "==================="
        
        # Calculate totals from CSV (skip header line)
        tail -n +2 target/site/jacoco/jacoco.csv | awk -F',' '
        BEGIN { 
            total_missed = 0; total_covered = 0; 
            branch_missed = 0; branch_covered = 0;
            line_missed = 0; line_covered = 0;
        }
        { 
            total_missed += $4; total_covered += $5;
            branch_missed += $6; branch_covered += $7;
            line_missed += $8; line_covered += $9;
        }
        END { 
            instruction_total = total_missed + total_covered;
            branch_total = branch_missed + branch_covered;
            line_total = line_missed + line_covered;
            
            if (instruction_total > 0) {
                instruction_coverage = (total_covered / instruction_total) * 100;
                printf "  Instructions: %.1f%% (%d/%d)\n", instruction_coverage, total_covered, instruction_total;
            }
            
            if (branch_total > 0) {
                branch_coverage = (branch_covered / branch_total) * 100;
                printf "  Branches:     %.1f%% (%d/%d)\n", branch_coverage, branch_covered, branch_total;
            }
            
            if (line_total > 0) {
                line_coverage = (line_covered / line_total) * 100;
                printf "  Lines:        %.1f%% (%d/%d)\n", line_coverage, line_covered, line_total;
            }
        }'
    fi
else
    echo "❌ Coverage report generation failed!"
    exit 1
fi
