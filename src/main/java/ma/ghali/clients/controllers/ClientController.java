package ma.ghali.clients.controllers;

import jakarta.validation.Valid;
import ma.ghali.clients.domaine.Client;
import ma.ghali.clients.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {

    private final ClientRepository clientRepo;

    @Autowired
    public ClientController(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/liste-clients";
    }

    @GetMapping("/nouveau-client")

    public String afficherFormulaireAjout(Client client) {
        return "ajouter-client";
    }

    @PostMapping("/enregistrer-client")
    public String actionAjouterClient(@Valid Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ajouter-client";
        }
        clientRepo.save(client);
        return "redirect:/liste-clients";
    }

    @GetMapping("/liste-clients")
    public String afficherListeClients(Model model) {
        model.addAttribute("clients", clientRepo.findAll());
        return "index";
    }

    @GetMapping("/modifier-client/{id}")
    public String afficherFormulaireCorrection(@PathVariable("id") long id, Model model) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable : " + id));
        model.addAttribute("client", client);
        return "modifier-client";
    }

    @PostMapping("/mettre-a-jour/{id}")
    public String actionMettreAJour(@PathVariable("id") long id, @Valid Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            client.setId(id);
            return "modifier-client";
        }
        clientRepo.save(client);
        return "redirect:/liste-clients";
    }

    @GetMapping("/effacer/{id}")
    public String actionSupprimerClient(@PathVariable("id") long id, Model model) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable : " + id));
        clientRepo.delete(client);
        return "redirect:/liste-clients";
    }
}
