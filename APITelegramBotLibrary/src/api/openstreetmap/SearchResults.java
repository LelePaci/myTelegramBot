/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.openstreetmap;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author pacie
 */
public class SearchResults {

    public String at_timestamp;
    public String at_attribution;
    public String at_querystring;
    public String at_excludePlaceIds;
    public String at_moreUrl;
    public boolean hasChilds;

    public List<Place> places;

    public SearchResults(Element e) {
        at_timestamp = e.hasAttribute("timestamp") ? e.getAttribute("timestamp") : null;
        at_attribution = e.hasAttribute("attribution") ? e.getAttribute("attribution") : null;
        at_querystring = e.hasAttribute("querystring") ? e.getAttribute("querystring") : null;
        at_excludePlaceIds = e.hasAttribute("exclude_place_ids") ? e.getAttribute("exclude_place_ids") : null;
        at_moreUrl = e.hasAttribute("more_url") ? e.getAttribute("more_url") : null;
        hasChilds = e.hasChildNodes();
        places = null;

        NodeList nPlaces = e.hasChildNodes() ? e.getElementsByTagName("place") : null;
        if (nPlaces.getLength() > 0) {
            places = new ArrayList();
            for (int i = 0; i < nPlaces.getLength(); i++) {
                places.add(new Place((Element) nPlaces.item(i)));
            }
        }
    }
}
