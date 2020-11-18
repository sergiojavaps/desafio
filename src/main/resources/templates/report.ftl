<!DOCTYPE html>
<html>
    <head>
        <title>Report Resume</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/style.css">
        </head>
    <body>
        <h2>Data Analisys</h2>
		
		<div>Summary of Output File Data</div>
	
        <table>
            <tr>
                <th> Number Customers </th>
                <th> Number Sellers </th>
                <th> Most Expensive Sale Id </th>
                <th> Worst Seller </th>
            </tr>

            <#list reportResumeList as report>
                <tr>
                    <td>${report.numberOfCustomers}</td>
                    <td>${report.numberOfSellers}</td>
                    <td>${report.mostExpensiveSaleId}</td>
                    <td>${report.worstSeller}</td>
                </tr>
            </#list>
        </table>

    </body>
</html>