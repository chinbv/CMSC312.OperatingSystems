# Operating System Simulator
This simulator is built in Java. Project developed by Brandon Chin, Bansri Rawal, and Vishakha Sehgal.
## How to run the OS Simulator
1. Download this GitHub repository. 
2. Launch Terminal or Command Prompt
3. cd into appropriate directory that holds this application
4. Please enter the following commands to launch the application:
```
cd CMSC312.OperatingSystems
cd src
javac Weeboo.java
java Weeboo
```
### Commands that may be entered into this OS's Terminal
**PROC** , 
**LOAD**,
**MEM**, 
**EXE**,
**RESET**,
**EXIT**

**Sample Commands:**
- `PROC `
    prints out PCB information for all unfinished processes
- `LOAD JobFile3.txt `
    loads specified job file into simulator
- `MEM `
    prints out memory allocation information
- `EXE `
    runs simulator until all processes are finished
- `EXE 80 `
    runs simulator for 80 cycles
- `RESET`
    the simulator is reset to start state. 
    
### Job Files
Structure:
  - First Line holds an integer which references a scheduler (1 = Round Robin, 2 = First In First Out, 3 = Shortest Job First)
  - Each following line has a LOAD command followed by the load time(an integer) for the process and process file name
  - Ends with an EXE command

Sample Job File:
```
3
LOAD 2 MEDIA_PLAYER.txt
LOAD 2 PHOTO_EDITOR.txt
LOAD 15 VIRUS_SCAN.txt
LOAD 25 WEB_BROWSER.txt
LOAD 26 WORD_PROCESSOR.txt
EXE
```

