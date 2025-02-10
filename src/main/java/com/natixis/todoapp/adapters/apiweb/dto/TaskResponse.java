package com.natixis.todoapp.adapters.apiweb.dto;

import java.util.UUID;

public record TaskResponse(UUID id, String label, boolean complete) {}