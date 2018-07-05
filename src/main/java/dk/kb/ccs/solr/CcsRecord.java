package dk.kb.ccs.solr;

import org.apache.solr.common.SolrDocument;
import org.json.JSONArray;
import org.json.JSONObject;

import dk.kb.ccs.utils.StringUtils;
import dk.kb.cumulus.utils.ArgumentCheck;

/**
 * The record for the CumulusCrowdService.
 * It envelopes the metadata fields from the Solr, to make the ready for being imported into Cumulus.
 * 
 * @author jolf
 */
public class CcsRecord {
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_RECORD_NAME = "local_id_ssi";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_TITEL = "title_tdsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_person = "cobject_person_ssim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_bygningsnavn = "area_building_tsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_sted = "cobject_location_ssim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_vejnavn = "citySection_street_tsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_husnummer = "citySection_housenumber_tsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_lokalitet = "cobject_building_ssim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_postnummer = "citySection_zipcode_tsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_by = "area_area_tsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_sogn = "area_parish_tsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_materikelnummer = "area_cadastre_tsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_note = "description_tsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_kommentar = "TODO";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_emneord = "subject_tdsim";
    /** The JSON field name for the record name.*/
    public static final String JSON_FIELD_FOR_georeference = "dcterms_spatial";
    
    
    public static final String JSON_ARRAY_STRING_SEPARATOR = ",";
    
    
    /** The name of the record.*/
    protected final String recordName;
    /** The name of the catalog.*/
    protected final String catalogName;

    /** The titel, imported as crowd_titel.*/
    protected String titel;
    /** The person, imported as crowd_person.*/
    protected String person;
    /** The bygningsnavn, imported as crowd_bygningsnavn.*/
    protected String bygningsnavn;
    /** The sted, imported as crowd_sted.*/
    protected String sted;
    /** The vejnavn, imported as crowd_vejnavn.*/
    protected String vejnavn;
    /** The husnummer, imported as crowd_husnummer.*/
    protected String husnummer;
    /** The lokalitet, imported as crowd_lokalitet.*/
    protected String lokalitet;
    /** The postnummer, imported as crowd_postnummer.*/
    protected String postnummer;
    /** The by, imported as crowd_by.*/
    protected String by;
    /** The sogn, imported as crowd_sogn.*/
    protected String sogn;
    /** The materikelnummer, imported as crowd_materikelnummer.*/
    protected String materikelnummer;
    /** The note, imported as crowd_note.*/
    protected String note;
    /** The kommentar, imported as crowd_kommentar.*/
    protected String kommentar;
    /** The emneord, imported as crowd_emneord.*/
    protected String emneord;
    /** The georeference, imported as crowd_georeference.*/
    protected String georeference;

    /**
     * Constructor. For JSON results.
     * @param solrData The JSON solr data object.
     * @param catalogName The name of the catalog
     */
    public CcsRecord(JSONObject solrData, String catalogName) {
        ArgumentCheck.checkTrue(solrData.has(JSON_FIELD_FOR_RECORD_NAME), 
                "JSONObject solrData must contain the field '" + JSON_FIELD_FOR_RECORD_NAME + "'");
        
        this.recordName = solrData.getString(JSON_FIELD_FOR_RECORD_NAME);
        this.catalogName = catalogName;
        
        this.titel = getJSONFieldValue(solrData, JSON_FIELD_FOR_TITEL);
        this.person = getJSONFieldValue(solrData, JSON_FIELD_FOR_person);
        this.bygningsnavn = getJSONFieldValue(solrData, JSON_FIELD_FOR_bygningsnavn);
        this.sted = getJSONFieldValue(solrData, JSON_FIELD_FOR_sted);
        this.vejnavn = getJSONFieldValue(solrData, JSON_FIELD_FOR_vejnavn);
        this.husnummer = getJSONFieldValue(solrData, JSON_FIELD_FOR_husnummer);
        this.lokalitet = getJSONFieldValue(solrData, JSON_FIELD_FOR_lokalitet);
        this.postnummer = getJSONFieldValue(solrData, JSON_FIELD_FOR_postnummer);
        this.by = getJSONFieldValue(solrData, JSON_FIELD_FOR_by);
        this.sogn = getJSONFieldValue(solrData, JSON_FIELD_FOR_sogn);
        this.materikelnummer = getJSONFieldValue(solrData, JSON_FIELD_FOR_materikelnummer);
        this.note = getJSONFieldValue(solrData, JSON_FIELD_FOR_note);
        this.kommentar = getJSONFieldValue(solrData, JSON_FIELD_FOR_kommentar);
        this.emneord = getJSONFieldValue(solrData, JSON_FIELD_FOR_emneord);
        this.georeference = getJSONFieldValue(solrData, JSON_FIELD_FOR_georeference);
    }
    
    /**
     * Constructor. For Solr results.
     * @param solrData The Solrj data object.
     * @param catalogName The name of the catalog
     */
    public CcsRecord(SolrDocument solrData, String catalogName) {
        ArgumentCheck.checkTrue(solrData.containsKey(JSON_FIELD_FOR_RECORD_NAME), 
                "JSONObject solrData must contain the field '" + JSON_FIELD_FOR_RECORD_NAME + "'");
        
        this.recordName = (String) solrData.getFieldValue(JSON_FIELD_FOR_RECORD_NAME);
        this.catalogName = catalogName;
        
        this.titel = getSolrFieldValue(solrData, JSON_FIELD_FOR_TITEL);
        this.person = getSolrFieldValue(solrData, JSON_FIELD_FOR_person);
        this.bygningsnavn = getSolrFieldValue(solrData, JSON_FIELD_FOR_bygningsnavn);
        this.sted = getSolrFieldValue(solrData, JSON_FIELD_FOR_sted);
        this.vejnavn = getSolrFieldValue(solrData, JSON_FIELD_FOR_vejnavn);
        this.husnummer = getSolrFieldValue(solrData, JSON_FIELD_FOR_husnummer);
        this.lokalitet = getSolrFieldValue(solrData, JSON_FIELD_FOR_lokalitet);
        this.postnummer = getSolrFieldValue(solrData, JSON_FIELD_FOR_postnummer);
        this.by = getSolrFieldValue(solrData, JSON_FIELD_FOR_by);
        this.sogn = getSolrFieldValue(solrData, JSON_FIELD_FOR_sogn);
        this.materikelnummer = getSolrFieldValue(solrData, JSON_FIELD_FOR_materikelnummer);
        this.note = getSolrFieldValue(solrData, JSON_FIELD_FOR_note);
        this.kommentar = getSolrFieldValue(solrData, JSON_FIELD_FOR_kommentar);
        this.emneord = getSolrFieldValue(solrData, JSON_FIELD_FOR_emneord);
        this.georeference = getSolrFieldValue(solrData, JSON_FIELD_FOR_georeference);
    }

    /**
     * Extracts String value of a given JSON field. Returns null, if the field does not exist.
     * @param solrData The JSON object to extract the string value from.
     * @param path The path to the JSON element.
     * @return The String value of the JSON element, or null if the element does not exist.
     */
    protected String getJSONFieldValue(JSONObject solrData, String path) {
        if(solrData.has(path)) {
            Object o = solrData.get(path);
            if(o instanceof JSONArray) {
                JSONArray array = (JSONArray) o;
                if(array.length() == 0) {
                    return null;
                } else if(array.length() == 1) {
                    return array.getString(0);
                }
                
                return StringUtils.extractJSONArray(array, JSON_ARRAY_STRING_SEPARATOR);
            } else {
                return (String) o;
            }
        } else {
            return null;
        }
    }

    /**
     * Extracts String value of a given JSON field. Returns null, if the field does not exist.
     * @param solrData The JSON object to extract the string value from.
     * @param path The path to the JSON element.
     * @return The String value of the JSON element, or null if the element does not exist.
     */
    protected String getSolrFieldValue(SolrDocument solrData, String path) {
        if(solrData.containsKey(path)) {
            return (String) solrData.getFieldValue(path);
        } else {
            return null;
        }
    }
    
    /** @return The name of the record.*/
    public String getRecordName() {
        return recordName;
    }
    /** @return The name of the catalog.*/
    public String getCatalogName() {
        return catalogName;
    }

    /** @return The titel, imported as crowd_titel.*/
    public String getTitel() {
        return titel;
    }
    /** @return The person, imported as crowd_person.*/
    public String getPerson() {
        return person;
    }
    /** @return The bygningsnavn, imported as crowd_bygningsnavn.*/
    public String getBygningsnavn() {
        return bygningsnavn;
    }
    /** @return The sted, imported as crowd_sted.*/
    public String getSted() {
        return sted;
    }
    /** @return The vejnavn, imported as crowd_vejnavn.*/
    public String getVejnavn() {
        return vejnavn;
    }
    /** @return The husnummer, imported as crowd_husnummer.*/
    public String getHusnummer() {
        return husnummer;
    }
    /** @return The lokalitet, imported as crowd_lokalitet.*/
    public String getLokalitet() {
        return lokalitet;
    }
    /** @return The postnummer, imported as crowd_postnummer.*/
    public String getPostnummer() {
        return postnummer;
    }
    /** @return The by, imported as crowd_by.*/
    public String getBy() {
        return by;
    }
    /** @return The sogn, imported as crowd_sogn.*/
    public String getSogn() {
        return sogn;
    }
    /** @return The materikelnummer, imported as crowd_materikelnummer.*/
    public String getMaterikelnummer() {
        return materikelnummer;
    }
    /** @return The note, imported as crowd_note.*/
    public String getNote() {
        return note;
    }
    /** @return The kommentar, imported as crowd_kommentar.*/
    public String getKommentar() {
        return kommentar;
    }
    /** @return The emneord, imported as crowd_emneord.*/
    public String getEmneord() {
        return emneord;
    }
    /** @return The georeference, imported as crowd_georeference.*/
    public String getGeoreference() {
        return georeference;
    }
}
