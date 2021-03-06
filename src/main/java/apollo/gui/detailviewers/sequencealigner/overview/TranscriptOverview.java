package apollo.gui.detailviewers.sequencealigner.overview;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;

import apollo.datamodel.ExonI;
import apollo.datamodel.SequenceEdit;
import apollo.datamodel.SequenceI;
import apollo.datamodel.Transcript;

public class TranscriptOverview extends JComponent {
  
  private Transcript transcript;
  private boolean drawIntrons;
  private IntervalComponent tran;
  
  int barHeight = 10;
  Color shiftColor = Color.black;
  
  public void paint(Graphics g) {
    super.paint(g);
    /*
    g.setFont(getFont());
    if (transcript == null)
      return;

    rh.removeAll();

    //    int length = editorPanel.
    //                 getSequenceForTier(editorPanel.getSelectedTier()).getLength();

    translationStart = transcript.getTranslationStart();
    int laststart_base = transcript.getPositionFrom(translationStart, 2);
    translationEnd = transcript.getTranslationEnd();
    int laststop_base = transcript.getLastBaseOfStopCodon();
    int readthrough_pos = transcript.readThroughStopPosition();
    int lastreadon_base = transcript.getPositionFrom(readthrough_pos, 2);

    int topDistance = (getSize().height - barHeight) / 2;
    java.util.Vector exons = transcript.getExons();
    int exon_count = exons.size();
    int featureWidth;
    
    // if we want to see the introns then set featureWidth to the transcript
    // length and draw a rectangle.
    if (drawIntrons) {
      featureWidth = (int) transcript.length();
      int lowPos = editorPanel.getAnnotationPanel()
        .basePairToPosition((int)transcript.getLow());
      int highPos = editorPanel.getAnnotationPanel()
        .basePairToPosition((int)transcript.getHigh());
      //int lineColorIndex = (editorPanel.getRangeIndex(tier,
        //                    lowPos,
        //                    highPos)
        //                    % colorList.length);
      
      //g.setColor(colorList[lineColorIndex][0]);
      g.setColor(editorPanel.getIndicatorColor());

      g.fillRect(margin,
                 (getSize().height - 2) / 2,
                 getSize().width - margin*2 - 1,
                 3);
      
    } else {
      featureWidth = 0;
      for(int i=0; i < exon_count; i++) {
        SeqFeatureI feature = (SeqFeatureI) exons.elementAt(i);
        featureWidth += (int) feature.length();
      }
    }

    double scaling_factor = ((double) featureWidth /
                             (double) (getSize().width - margin*2));

    int x = margin;
    // Loop through exons
    for(int i=0; i < exon_count; i++) {
      ExonI exon = transcript.getExonAt(i); 
      int low = (int) exon.getLow();
      int high = (int) exon.getHigh();
      int lowPos = editorPanel.getAnnotationPanel().basePairToPosition(low);
      int highPos = editorPanel.getAnnotationPanel().basePairToPosition(high);

      int width = (int) Math.floor(exon.length() / scaling_factor);
      if (width < 1)
        width = 1;

     // int transcriptColorIndex = (editorPanel.getRangeIndex(tier,
    //                              lowPos,
    //                              highPos)
    //                              % colorList.length);

    //  int exonColorIndex = (editorPanel.getExonRangeIndex(tier,
    //                        lowPos,
    //                        highPos)
    //                        % colorList[transcriptColorIndex].length);

     // g.setColor(colorList[transcriptColorIndex][exonColorIndex]);
      g.setColor(Color.blue); //TODO: change
      
      int drawX = x;

      rh.put(drawX, drawX+width-1, exon);

      g.fillRect(drawX,
                 topDistance,
                 width,
                 barHeight+1);

      // paint the codons
      paintCodon (g,
                  exon, 
                  translationStart, laststart_base, 
                  low, high,
                  DrawableUtil.getStartCodonColor(transcript),
                  x, topDistance, scaling_factor);

      paintCodon (g,
                  exon, 
                  translationEnd, laststop_base, 
                  low, high,
                  Color.red,
                  x, topDistance, scaling_factor);

      paintCodon (g,
                  exon, 
                  readthrough_pos, lastreadon_base, 
                  low, high,
                  Color.pink,
                  x, topDistance, scaling_factor);

      // If exon is coding draw in frame number - what is the first frame?X
      paintFrame(g, exon, low, high, translationStart, laststop_base,
                 x, topDistance, scaling_factor);

      // increment x
      x += width;
      if (drawIntrons && (i + 1) < exon_count) {
        ExonI nextExon = transcript.getExonAt (i+1);
        int intron_len = (int) (exon.getStrand() == 1 ?
                                (nextExon.getLow() - high + 1) :
                                (low - nextExon.getHigh() + 1));
        x += (intron_len / scaling_factor);
      }
    } // end of exon loop

    int baseStart = editorPanel.getVisibleBase();
    int baseCount = editorPanel.getVisibleBaseCount();
    int baseOffset = baseStart - (int) transcript.getStart();
    if (editorPanel.getStrand() == Strand.REVERSE)
      baseOffset = (int) transcript.getStart() - baseStart;
    int pixelOffset = (int) ((double) baseOffset / scaling_factor);
    int basePixelStart = margin+pixelOffset;
    int basePixelCount = (int) ((double) baseCount /
                                (double) scaling_factor);

    //    g.setColor(Color.yellow);
    // Box that goes over exon diagram at the bottom to show which region
    // you're seeing in the detailed view
    g.setColor(Color.black); //TODO config
    g.drawRect(basePixelStart, topDistance-4,
               basePixelCount, barHeight+8);
    // To make a two-pixel-wide rectangle, draw a one-pixel-bigger rectangle
    // around the first one
    g.drawRect(basePixelStart-1, topDistance-5,
               basePixelCount+2, barHeight+10);*/
  }

  public boolean getDrawIntrons() {
    return drawIntrons;
  }
  
  public void setDrawIntrons(boolean drawIntrons) {
    this.drawIntrons = drawIntrons;
    repaint();
  }
  
  
  private void paintCodon (Graphics g,
      ExonI exon, 
      int first_base, 
      int last_base,
      int low,
      int high,
      Color codon_color,
      int x, 
      int topDistance,
      double scaling_factor) {

    int expected_last = first_base + (2 * exon.getStrand());
    if (first_base > 0) {
      if (first_base >= low && first_base <= high) {
        g.setColor(codon_color);
        // account for stop codons that span exon boundaries
        int codon_width = (last_base != expected_last ?
            Math.abs(exon.getEnd() - first_base) + 1 : 3);
        int offset = (int) (Math.abs(exon.getStart() - first_base) /
            scaling_factor);
        int rect_width = (int) (((double) codon_width) / scaling_factor);
        if (rect_width < 1)
          rect_width = 1;
        g.fillRect(x + offset,
            topDistance,
            rect_width,
            barHeight+1);
      }

      if (last_base != expected_last &&
          last_base >= low &&
          last_base <= high) {
        g.setColor(codon_color);
//      account for stop codons that span exon boundaries
        int codon_width = Math.abs(last_base - exon.getStart()) + 1;
        int rect_width = (int) (((double) codon_width) / scaling_factor);
        if (rect_width < 1)
          rect_width = 1;
        g.fillRect(x,
            topDistance,
            rect_width,
            barHeight+1);
      }
    }
  }

  private void paintBase (Graphics g,
      ExonI exon, 
      int base_pos, 
      int low,
      int high,
      Color base_color,
      int x, 
      int topDistance,
      double scaling_factor) {
    if (base_pos > 0) {
      if (base_pos >= low && base_pos <= high) {
        g.setColor(base_color);
//      account for stop codons that span exon boundaries
        int offset = (int) (Math.abs(exon.getStart() - base_pos) /
            scaling_factor);
        int rect_width = (int) (((double) 1) / scaling_factor);
        if (rect_width < 1)
          rect_width = 1;
        g.fillRect(x + offset,
            topDistance,
            rect_width,
            barHeight+1);
      }
    }
  }

//draws the frame number for this exon?
  private void paintFrame(Graphics g, ExonI exon, 
      int low, int high, 
      int translationStart, int laststop_base,
      int exon_x,
      int topDistance, double scaling_factor) {

    boolean coding = exon.containsCoding();
    int frame = exon.getFrame();
    FontMetrics metrics = getFontMetrics(getFont());
    SequenceEdit [] edit_list = exon.buildEditList();
    int edits = (edit_list != null ? edit_list.length : 0);
    int x = exon_x;
    int prev_pos;
    if (exon.contains(translationStart)) {
      prev_pos = translationStart;
      int length = Math.abs(exon.getStart() - prev_pos) + 1;
      int width = (int) Math.floor(length / scaling_factor);
      if (width < 1)
        width = 1;
      x += width;
    } else {
      prev_pos = exon.getStart();
      x = exon_x;
    }

    int last_pos = (exon.contains(laststop_base) ? 
        laststop_base : exon.getEnd());

    for (int i = 0; i < edits; i++) {
      SequenceEdit edit = edit_list[i];
      int pos = edit.getPosition();
      paintBase(g,
          exon,
          pos, low, high,
          shiftColor,  // was Color.yellow
          exon_x, topDistance, scaling_factor);
      if (coding) {
        g.setColor(getForeground());
        String drawMe = ""+(frame);
        int strWidth = metrics.stringWidth(drawMe);
        int length = Math.abs(pos - prev_pos) + 1;
        int width = (int) Math.floor(length / scaling_factor);
        if (width < 1)
          width = 1;
        int fontx = x + width / 2 - (strWidth / 2);
        int fonty = topDistance + barHeight / 2 + getFont().getSize() / 2;

        g.drawString(drawMe, fontx, fonty);

        x += width;
        prev_pos = pos;
        if (edit.getEditType().equals(SequenceI.DELETION)) {
          frame = (frame == 3 ? 1 : frame - 1);
        }
        if (edit.getEditType().equals(SequenceI.INSERTION)) {
          frame = (frame == 1 ? 3 : frame + 1);
        }
      }
    }
    if (coding) {
      g.setColor(getForeground());
      String drawMe = ""+(frame);
      int strWidth = metrics.stringWidth(drawMe);
      int length = Math.abs(last_pos - prev_pos) + 1;
      int width = (int) Math.floor(length / scaling_factor);
      if (width < 1)
        width = 1;
      int fontx = x + width / 2 - (strWidth / 2);
      int fonty = topDistance + barHeight / 2 + getFont().getSize() / 2;
      g.drawString(drawMe, fontx, fonty);
      x += width;
    }
  }

}
