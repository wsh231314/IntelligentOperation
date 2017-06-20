try:
    initEvidence()
    import xlrd
    data = xlrd.open_workbook('C:/Users/shawn.shaohua.wang/git/autoScreen/sikuli_ide/excelApl/evidences/scripts/script_72/20170410/20170410183642/data_20170410183642.xls')
    sheet = data.sheets()[0]
    intDataStartRow = 0
    for i in range(1):
        iColumn = 1
        click(Pattern('PAGE_66_FIELD_1.png').targetOffset(52,71))
        captureNow()
        click('PAGE_66_FIELD_2.png')
        captureNow()
    import xlrd
    data = xlrd.open_workbook('C:/Users/shawn.shaohua.wang/git/autoScreen/sikuli_ide/excelApl/evidences/scripts/script_72/20170410/20170410183642/data_20170410183642.xls')
    sheet = data.sheets()[0]
    intDataStartRow = 4
    for i in range(1):
        iColumn = 1
        if exists('PAGE_68_FIELD_1.png') :
            strOperationData = sheet.row(intDataStartRow + i)[iColumn + 0].value
            type('PAGE_68_FIELD_1.png', strOperationData)
            click('PAGE_68_FIELD_7.png')
            captureNow()
        iColumn = iColumn + 1
        if not exists('PAGE_68_FIELD_8.png') :
            captureNow()
            if not exists('PAGE_68_FIELD_1.png') :
                click('PAGE_68_FIELD_2.png')
                wait('PAGE_68_FIELD_1.png')
                strOperationData = sheet.row(intDataStartRow + i)[iColumn + 0].value
                type('PAGE_68_FIELD_1.png', strOperationData)
                click('PAGE_68_FIELD_7.png')
        iColumn = iColumn + 1
    popup('the end!')
except Exception, ex:
    popup('error!')
finally:
    endEvidence()
