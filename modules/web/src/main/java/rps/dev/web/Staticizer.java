package rps.dev.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.StreamedSource;

public class Staticizer {

    private final Set<String> rsc = new HashSet<String>();
    private final String contextPath;

    public Staticizer(String context) {
        this.contextPath = context;

    }

    public void parse(String url) {
        rsc.add(url);

        try {
            StreamedSource ss = new StreamedSource(new URL(this.contextPath + "/" + url));
            Segment htmlSegment = null;
            for (Iterator<Segment> is = ss.iterator(); is.hasNext() && (htmlSegment = is.next()) != null;) {
                if (htmlSegment instanceof StartTag) {
                    try {
                        ResourceExtract re = ResourceExtract.valueOf(((StartTag) htmlSegment).getName());
                        String rscUrl = re.url((StartTag) htmlSegment);
                        if (rscUrl != null) {
                            rsc.add(rscUrl);
                        }
                    } catch (IllegalArgumentException e) {
                    }
                }
            }
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(String baseDir) {
        final String[] files = this.rsc.toArray(new String[] {});
        // final AtomicInteger filesIndex = new AtomicInteger(0);

        for (String file : files) {
            try {
                // String file = files[filesIndex.getAndIncrement()];
                InputStream is = new URL(this.contextPath + file + "?min=y").openStream();

                File f = new File(baseDir + '/' + file);
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                OutputStream out = new FileOutputStream(f);

                byte[] buffer = new byte[512];
                int readBytes = 0;
                while ((readBytes = is.read(buffer)) > 0) {
                    out.write(buffer, 0, readBytes);
                }

                out.flush();
                out.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    enum ResourceExtract {
        a {
            @Override
            String url(StartTag s) {
                return s.getAttributeValue("href");
            }
        },
        link {
            @Override
            String url(StartTag s) {
                return s.getAttributeValue("href");
            }
        },
        script {
            @Override
            String url(StartTag s) {
                return s.getAttributeValue("src");
            }
        };
        abstract String url(StartTag s);
    }

    public static void main(String[] args) {
        Staticizer s = new Staticizer("http://localhost:8080/dfweb/");
        // s.parse("dishes.html");
        s.parse("dishes.html");
        s.load("r:/tmp/dd");
    }
}
