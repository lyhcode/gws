import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import javax.swing.*
import java.awt.*

class FileBinaryCategory{    
    def static leftShift(File file, URL url){    
       url.withInputStream {is->
            file.withOutputStream {os->
                def bs = new BufferedOutputStream( os )
                bs << is                    
            }
        } 
    }    
}

def ant = new AntBuilder();

new SwingBuilder().edt {
	frame(title:'Frame', size:[300,300], defaultCloseOperation:JFrame.EXIT_ON_CLOSE, show: true) {
		borderLayout()
			textlabel = label(text:"訊息", constraints: BL.NORTH)
			button(
				text:'下載教材壓縮檔',
				actionPerformed: {
					textlabel.text = "開始下載"
					
					def dir = new File('myplweb')
					if (!dir.exists()) {
						dir.mkdir()
					}

					def file = new File("plweb.zip")
					use (FileBinaryCategory)
					{
						file << "http://gws.lyhdev.com/plweb.zip".toURL()
					}
					textlabel.text = file.absolutePath
					ant.unzip(src: file, dest: dir, overwrite: 'true') {
						mapper(type: 'flatten')
					};
				},
				constraints:BL.SOUTH
			)
	}
}
