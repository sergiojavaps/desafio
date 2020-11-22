<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Data Analysis Agibank</title>
    </head>

    <body onload="updateSize();">
        
        <form name="uploadingForm" enctype="multipart/form-data" action="${appContextName}/up" method="POST">
            <p>
                <input id="fileInput" type="file" name="uploadingFiles" onchange="updateSize();" multiple>
                selected files: <span id="fileNum">0</span>;
                total size: <span id="fileSize">0</span>
            </p>
            <p>
                <input type="submit" value="Upload files"> 
                
                <a href="report">Show Data Analysis Result Reports</a>
                
            </p>
        </form>
        
        <div style="width:100%; background-color:blue;">
	        <div style="width:15%; height: 30%; display:inline-block; float:left; ">
	            <div>Uploaded files:</div>
	            <#list files as file>
	            <div>
	            ${file.getName()}
	            </div>
	            </#list>
	        </div>
	        
	        <div style="width:12%; height: 30%; display:inline-block; float:left; ">
	            <div>Processed files:</div>
	            <#list procFiles as filep>
	            <div>
	            ${filep.getName()}
	            </div>
	            </#list>
	        </div>
	        
	        <div style="width:12%; height: 30%; display:inline-block; float:left; ">
	            <div>Failed processed files:</div>
	            <#list failFiles as failFile>
	            <div>
	            ${failFile.getName()}
	            </div>
	            </#list>
	        </div>
	        
	        <div style="width:12%; height: 30%; display:inline-block; float:left; ">
	            <div>Result of file processing:</div>
	            <#list result as filer>
	            <div>
	            ${filer.getName()}
	            </div>
	            </#list>
	        </div>
	        
	        <div style="width:12%; height: 30%; display:inline-block; float:left; ">
	            <div>Directories created by the system:</div>
	            <#list dirList as dir>
	            <div>
	            ${dir}
	            </div>
	            </#list>
	        </div>
	        
        </div>
        
        <script>
            function updateSize() {
                var nBytes = 0,
                        oFiles = document.getElementById("fileInput").files,
                        nFiles = oFiles.length;
                for (var nFileId = 0; nFileId < nFiles; nFileId++) {
                    nBytes += oFiles[nFileId].size;
                }
                var sOutput = nBytes + " bytes";
                // optional code for multiples approximation
                for (var aMultiples = ["KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB"], nMultiple = 0, nApprox = nBytes / 1024; nApprox > 1; nApprox /= 1024, nMultiple++) {
                    sOutput = nApprox.toFixed(3) + " " + aMultiples[nMultiple] + " (" + nBytes + " bytes)";
                }
                // end of optional code
                document.getElementById("fileNum").innerHTML = nFiles;
                document.getElementById("fileSize").innerHTML = sOutput;
            }
            
           
        </script>
    </body>
</html>