import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


public class app {

	protected Shell shell;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
			
			app window = new app();
			window.open();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		shell = new Shell();
		shell.setSize(800, 599);
		shell.setText("SWT Application");
		shell.setLayout(new FormLayout());
	
		ExampleFileFilter filter = new ExampleFileFilter();

		final Button loadFacebookData = new Button(shell, SWT.NONE);
		loadFacebookData.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				
				loadFacebookData.setEnabled(false);
				
				JFileChooser chooser = new JFileChooser("C:\\Users\\David\\Desktop\\facebook-\\jdpneto\\html");
//					
//					private static final long serialVersionUID = 1L;
					shell.setVisible(false);

//					@Override
//					   protected JDialog createDialog(Component parent) throws HeadlessException {
//					       // intercept the dialog created by JFileChooser
//					       JDialog dialog = super.createDialog(parent);
//					       dialog.setModal(true);  // set modality (or setModalityType)
//					       return dialog;
//					   }
//				};
				ExampleFileFilter filter = new ExampleFileFilter();
				filter.addExtension("html");
				filter.setDescription("load messages.html");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(chooser);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
						System.out.println(count(chooser.getSelectedFile()));
						loadMessages(chooser.getSelectedFile());
					loadFacebookData.setVisible(false);
					shell.setVisible(true);
				}
			}
});
		loadFacebookData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		FormData fd_loadFacebookData = new FormData();
		fd_loadFacebookData.bottom = new FormAttachment(100, -182);
		fd_loadFacebookData.left = new FormAttachment(0, 258);
		fd_loadFacebookData.right = new FormAttachment(0, 522);
		fd_loadFacebookData.top = new FormAttachment(0, 185);
		loadFacebookData.setLayoutData(fd_loadFacebookData);
		loadFacebookData.setText("Load Facebook Messages");

	}
	
	public boolean loadMessages(File f)
	{
		BufferedReader br;
		boolean foundTitle = false;
        boolean isMessageFile = false;
        String myName = "";
	    try {
	    	br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f), "UTF8"));
	        String line = br.readLine();
	        Pattern r;
	        Matcher m;
	        while (!foundTitle && line != null ) {
	        	String title = "(?i)(<title.*?>)(.+?)(</title>)";
	        	r = Pattern.compile(title);
	        	m = r.matcher(line);
	        	if(m.find( ))
	        	{
	        		foundTitle = true;
	        		String pattern = "(?i)(<title.*?>)(.+?)( -)(.+?)(Messages)(.+?)(</title>)";
	        		r = Pattern.compile(pattern);
		        	m = r.matcher(line);
		        	if(m.find( ))
		        	{
		        		myName = m.group(2);
		        		isMessageFile = true;
		        	}
	        	}
	            line = br.readLine();
	        }
	        
	        System.out.println(myName);
	        String pattern = "(<span class=\"profile fn\">)([\\w\\s„ıÈ·]+?)(</span>, <span class=\"profile fn\">)("+myName+")(</span>)";
	        Pattern startChatPattern = Pattern.compile(pattern);
	        
	        
            while (isMessageFile && line != null )
            {
            	
            	m = startChatPattern.matcher(line);
            	if(m.find())
            		System.out.println(m.group(2));
            	
            	line = br.readLine();
            	
            }
	        br.close();
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    System.out.println("end");
	    return isMessageFile;
	}
	
	public int count(File file) {
	    InputStream is;
	    try {
	    	is = new BufferedInputStream(new FileInputStream(file));
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    ++count;
	            }
	        }
	        is.close();
	        return (count == 0 && !empty) ? 1 : count;
	        
	    }catch(Exception e){ return 1; } 
	    
	}

}
