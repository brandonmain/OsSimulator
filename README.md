# OsSimulator
Simulated operating system written in Java 8.

The OS will take in a specific configuration file and a specific meta data file to load and run an operating system.

### Example Configuration File

Start Simulator Configuration File
Version/Phase: 1.0
File Path: Test_3a.mdf
Monitor display time {msec}: 20
Processor cycle time {msec}: 10
Scanner cycle time {msec}: 10
Hard drive cycle time {msec}: 15
Keyboard cycle time {msec}: 50
Memory cycle time {msec}: 30
Projector cycle time {msec}: 10
Log: Log to Both
Log File Path: logfile_1.lgf
End Simulator Configuration File


### Example Meta Data File

Start Program Meta-Data Code:
S{begin}0; A{begin}0; P{run}11; M{allocate}2;
O{monitor}7; I{hard drive}8; O{projector}20; A{finish}0;
A{begin}0; M{allocate}4; O{projector}6; M{allocate}3; I{hard drive}7;
O{hard drive}2; O{hard drive}16; M{allocate}4;
P{run}6; O{projector}4; A{finish}0; A{begin}0; M{block}6; I{keyboard}17;
M{block}4; P{run}5; P{run}5; O{hard drive}6;
P{run}18; A{finish}0; S{finish}0.
End Program Meta-Data Code.
