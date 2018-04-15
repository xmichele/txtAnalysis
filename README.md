# txtAnalysis
Application that search for most frequent words in a text File or in a group of them and generates a report.

Build with Java SE 1.8, the reference main is in the TextFilesAnalysis java class.
java -jar your_appname.jar inpath_java_conv outpath_report_java_conv
Java string convention for the path implies double backslash between levels, i.e. C:\\myfolder\\myfile.txt
The input path shall contain all the text files of interest: files without ".txt" or ".log" extensions or in inner depth levels will be discarded. If the output file already exsists, it will be replaced by the new generated one.

Junit 4.12 for the tests related to the BaseTests and ITests class.
In the Resources folder there is an input test txt file (a GPS NMEA stream) and the "outSummm.txt" file is an example of the output report.
