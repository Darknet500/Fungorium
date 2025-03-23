$ErrorActionPreference = "Stop"

$srcDir = "src"
$outDir = "out"

# Create the output directory if it doesn't exist
if (!(Test-Path -Path $outDir)) {
    New-Item -ItemType Directory -Path $outDir | Out-Null
}

# Compile all Java files
Write-Host "Compiling Java files..."
javac -d $outDir (Get-ChildItem -Path $srcDir -Recurse -Filter "*.java").FullName

# Check if compilation was successful
if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "Compilation successful!" -ForegroundColor Green

$mainClass = "Controll.Main"
Write-Host "Running $mainClass..."
java -cp $outDir $mainClass