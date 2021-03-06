package fr.treeptik.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import fr.treeptik.model.Evaluation;
import fr.treeptik.model.Formation;
import fr.treeptik.model.Session;
import fr.treeptik.model.Stagiaire;
import fr.treeptik.service.FormationEJB;
import fr.treeptik.service.SessionEJB;
import fr.treeptik.service.StagiaireEJB;

@ManagedBean
@SessionScoped
public class StagiaireController {

	// *********ENTITE******************************************************
	private Formation formation = new Formation();
	private Evaluation evaluation = new Evaluation();
	private Stagiaire stagiaire = new Stagiaire();
	private Session session = new Session();
	
	// *********EJB*********************************************************
	@EJB
	private SessionEJB sessionEJB;
	@EJB
	private StagiaireEJB stagiaireEJB;
	@EJB
	private FormationEJB formationEJB;
	@EJB
	private SendTextMessage gestionmail;


	// **********LISTES*****************************************************
	private List<Stagiaire> listStagiaires = new ArrayList<Stagiaire>();
	private List<SelectItem> selectStagiaire;
	
	// **********DATAMODEL**************************************************
	@SuppressWarnings("rawtypes")
	private DataModel lDMStagiaires;

	public String doCreate() {
		stagiaireEJB.create(stagiaire);
		gestionmail.mailCreationUser(stagiaire);
		getlDMStagiaires();
		return "messageStagiaireCree";
	}

	@SuppressWarnings("rawtypes")
	public String doDelete() {
		Stagiaire stagiaire = (Stagiaire) lDMStagiaires.getRowData();
		stagiaireEJB.delete(stagiaire);
		lDMStagiaires = new ListDataModel();
		lDMStagiaires.setWrappedData(stagiaireEJB.findAll());
		return "listStagiaires";
	}

	public String doSelectUpdate() {
		stagiaire = (Stagiaire) lDMStagiaires.getRowData();
		System.out.println("stagiaire"+stagiaire);
		System.out.println("stagiairenom"+stagiaire.getNom());
		return "updateStagiaire";
	}

	@SuppressWarnings("rawtypes")
	public String doUpdate() {
		stagiaireEJB.update(stagiaire);
		lDMStagiaires = new ListDataModel();
		lDMStagiaires.setWrappedData(stagiaireEJB.findAll());
		return "messageStagiaireUpdate";
	}

	public String doFindAll() {
		listStagiaires = stagiaireEJB.findAll();
		return "listStagiaires";
	}

	public List<SelectItem> getSelectStagiaire() {
		
		listStagiaires = stagiaireEJB.findAll();
		selectStagiaire = new ArrayList<SelectItem>();
		for (Stagiaire stagiaire : listStagiaires) {
			selectStagiaire.add(new SelectItem(stagiaire.getId(), stagiaire
					.getNom() + " " + stagiaire.getPrenom()));
		}
		return selectStagiaire;
	}

	public void setSelectStagiaire(List<SelectItem> selectStagiaire) {
		this.selectStagiaire = selectStagiaire;
	}
	@SuppressWarnings("rawtypes")
	public DataModel getlDMStagiaires() {
		if (lDMStagiaires == null) {
			lDMStagiaires = new ListDataModel();
			lDMStagiaires.setWrappedData(stagiaireEJB.findAll());
		}
		return lDMStagiaires;
	}

	@SuppressWarnings("rawtypes")
	public void setlDMStagiaires(DataModel lDMStagiaires) {
		this.lDMStagiaires = lDMStagiaires;
	}
	
	public List<Stagiaire> getListStagiaires() {
		return listStagiaires;
	}

	public void setListStagiaires(List<Stagiaire> listStagiaires) {
		this.listStagiaires = listStagiaires;
	}

	public StagiaireEJB getStagiaireEJB() {
		return stagiaireEJB;
	}

	public void setStagiaireEJB(StagiaireEJB stagiaireEJB) {
		this.stagiaireEJB = stagiaireEJB;
	}
	
	public SessionEJB getSessionEJB() {
		return sessionEJB;
	}

	public void setSessionEJB(SessionEJB sessionEJB) {
		this.sessionEJB = sessionEJB;
	}
	public FormationEJB getFormationEJB() {
		return formationEJB;
	}

	public void setFormationEJB(FormationEJB formationEJB) {
		this.formationEJB = formationEJB;
	}
	
	public Stagiaire getStagiaire() {
		return stagiaire;
	}

	public void setStagiaire(Stagiaire stagiaire) {
		this.stagiaire = stagiaire;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	
	@SuppressWarnings("unused")
	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	// *********************Test PRIMEFACES******************
	private List<Stagiaire> filteredStagiaires;
	private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("nom",
			"prenom", "ville", "domaine", "sexe", "codePostal", "diplome");
	private List<ColumnModel> columns = new ArrayList<ColumnModel>();
	private String columnTemplate = "nom prenom ville";

	public List<ColumnModel> getColumns() {
		return columns;
	}

	@SuppressWarnings("serial")
	static public class ColumnModel implements Serializable {

		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}

	public String getColumnTemplate() {
		return columnTemplate;
	}

	public void setColumnTemplate(String columnTemplate) {
		this.columnTemplate = columnTemplate;
	}

	public void updateColumns() {
		// reset table state
		UIComponent table = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent(":form:listStagiaires");
		table.setValueExpression("sortBy", null);

		// update columns
		createDynamicColumns();
	}

	public void createDynamicColumns() {
		String[] columnKeys = columnTemplate.split(" ");
		columns.clear();

		for (String columnKey : columnKeys) {
			String key = columnKey.trim();

			if (VALID_COLUMN_KEYS.contains(key)) {
				columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
			}
		}
	}

	public List<Stagiaire> getFilteredStagiaires() {
		return filteredStagiaires;
	}

	public void setFilteredStagiaires(List<Stagiaire> filteredStagiaires) {
		this.filteredStagiaires = filteredStagiaires;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}
}
