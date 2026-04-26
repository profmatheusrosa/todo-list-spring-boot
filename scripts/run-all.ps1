<#
Quick script to start backend and frontend in separate PowerShell windows.
Run from repository root: `scripts\run-all.ps1`
#>

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$repoRoot = Resolve-Path (Join-Path $scriptDir "..")

Write-Host "Starting backend in new window..."
Start-Process powershell -ArgumentList "-NoExit","-Command","cd '$repoRoot'; mvn spring-boot:run"

Write-Host "Starting frontend in new window..."
Start-Process powershell -ArgumentList "-NoExit","-Command","cd '$repoRoot\frontend'; npm install; npm start"
